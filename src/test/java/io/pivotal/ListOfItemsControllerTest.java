package io.pivotal;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringdoApplication.class)
@WebAppConfiguration
public class ListOfItemsControllerTest {


    private MockMvc mvc;



    @Autowired
    ItemRepository itemRepository;

    @Autowired
    private WebApplicationContext context;


    // Instead of the two annotations above, you could also write
    //    @Autowired
    //    ListOfItemsControllerTest(ItemRepository itemRepository, WebApplicationContext context) {
    //        this.itemRepository = itemRepository;
    //        this.context = context;
    //    }

    @Before
    public void setUp() throws Exception {
        /* do not use standAloneSetup, as it is not good for web apps */
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void whenDefaultPlaceholdersLoadedTwoTasksShouldShow() throws Exception {
        // note that we are only testing spring json output, we are assuming angular shows this \
        // nicely on the user's page

        // we assume that our first version always has two tasks:
        // id1 / title: Go for a swim  / Content:  Go swimming on Monday night
        // id2 / title: Visit farmer's market / Content: Buy dairy and eggs at farmers market on Wednesday

        mvc.perform(get("/resource/dummylist/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[1].id", is("2")))
                .andExpect(jsonPath("$[0].title", containsString("swim")))
                .andExpect(jsonPath("$[1].title", containsString("market")))
                .andExpect(jsonPath("$[0].content", containsString("swimming on Monday")))
                .andExpect(jsonPath("$[1].content", containsString("dairy and eggs")));
    }

    @Test
    public void whenItemIsCheckedAsDoneModelIsUpdated() throws Exception {
        Item item = new Item("Fake Todo", "Do Lots of stuff");
        // post item into db
        itemRepository.save(item);

        // call the backend function and set the 'done' to 'yes' for this item
        mvc.perform(post(String.format("/resource/done/%d/yes/", item.id)))
                .andDo(print())
                .andExpect(status().isOk());
        // retrieve updated item from db
        Item newItem = itemRepository.findOne(item.id);
        // check that for updated item, done == yes, while
        assertEquals(item.done, "no");
        assertEquals(newItem.done, "yes");
    }

    @Test
    public void whenEditIsMadeTheModelIsUpdated() throws Exception {
        // setup
        String newtitle = "New Test Title";
        Item item = new Item("Test Todo", "Do Lots of stuff");
        itemRepository.save(item);
        // make edit 1
        mvc.perform(post(String.format("/resource/save/%d/%s/%s/no/", item.id, newtitle, item.content)))
                .andExpect(status().isOk());
        assertNotEquals(item.title, newtitle);
        Item newItem = itemRepository.findOne(item.id);
        assertEquals(newItem.title, newtitle);
        assertEquals(newItem.content, item.content);

    }

    @Test
    public void whenCreateIsHitANewItemIsCreatedAndReturned() throws Exception {
        mvc.perform(get("/resource/create/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.title", is("")))
                .andExpect(jsonPath("$.content", is("")))
                .andExpect(jsonPath("$.done", is("no")));

    }
}
