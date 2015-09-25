package io.pivotal;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.fest.assertions.Assertions.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = SpringdoApplication.class)
@WebAppConfiguration
public class ListOfItemsControllerTest {


    private MockMvc mvc;



    @Autowired
    ItemRepository itemRepository;

//    ListOfItemsControllerTest(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @Autowired
    private WebApplicationContext context;


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
                //.andExpect(view().name("list_of_items"))
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
        // post item into db here
        itemRepository.save(item);

        mvc.perform(post("/resource/done/"+item.id+"/yes/"))
                .andDo(print())
                .andExpect(status().isOk());
        // retrieve item from db,
        Item newItem = itemRepository.findOne(item.id);
        // check that done == yes
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
        assertThat(newtitle).isEqualTo(newItem.title); //fest
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
