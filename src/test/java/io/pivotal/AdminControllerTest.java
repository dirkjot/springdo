package io.pivotal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringdoApplication.class)
@WebAppConfiguration
public class AdminControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Before
    public void setUp() throws Exception {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @WithMockUser("Navya")
    @Test
    public void AdminList_returnsAListOfAllItemsForAdmin() throws Exception {
        mvc.perform(get("/resource/admin/list/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(5)))
                .andExpect(jsonPath("$[0].title", containsString("swim")))  // owned by Navya
                .andExpect(jsonPath("$[4].title", containsString("sleep")));  // owned by Dirk
    }

    @WithMockUser("Dirk")
    @Test
    public void AdminList_returnsAListOfOwnItemsForUser() throws Exception {
        mvc.perform(get("/resource/admin/list/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].title", containsString("sleep")));
    }

    @WithMockUser("Navya")
    @Test
    public void UserList_returnsTwoUserForAdmin() throws Exception {
        mvc.perform(get("/resource/admin/userlist/"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @WithMockUser("Dirk")
    @Test
    public void UserListTest_returnsNoItemsForUser() throws Exception {
        mvc.perform(get("/resource/admin/userlist/"))
                .andExpect(status().isOk());
    }

    @WithMockUser("Navya")
    @Test
    public void AdminShowUser_worksForAdmin() throws Exception {
        mvc.perform(get("/resource/user/Dirk/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username", containsString("Dirk")))
                .andExpect(jsonPath("$.email", containsString("t@example.com")));
    }

    @Test
    public void AdminShowUser_failsWithoutAuthentication() throws Exception {
        mvc.perform(get("/resource/user/Navya/"))
                .andExpect(unauthenticated());
    }

    @WithMockUser("Dirk")
    @Test
    public void WhoIsLoggedIn_returnsUsernameWhenLoggedIn() throws Exception {
        mvc.perform(get("/who/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", containsString("Dirk")));

    }

    @Test
    public void WhoIsLoggedIn_returnsEmptyOtherwise() throws Exception {
        mvc.perform(get("/who/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", isEmptyString()));
    }
}