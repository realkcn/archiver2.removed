package org.kbs.archiver.web.controller;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.kbs.archiver.StableTest;
import org.kbs.archiver.model.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration(value = "src/main/webapp")
@ContextConfiguration(locations = {"classpath:spring-test.xml"})
@Category(StableTest.class)
public class BoardControllerTest {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }

    @Test
    public void testGetAll() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/listboard.do"))
                .andExpect(MockMvcResultMatchers.view().name("listboard"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("boards")).andReturn();
        Assert.assertNotNull(result.getModelAndView().getModel().get("boards"));
        assertTrue(result.getModelAndView().getModel().get("boards") instanceof Collection<?>);
        Collection<Board> boards=(Collection<Board>)result.getModelAndView().getModel().get("boards");
        assertEquals(boards.size(),2);
    }
}