package com.suslov;

import com.suslov.persist.model.Category;
import com.suslov.persist.repo.CategoryRepository;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;


import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@SpringBootTest
public class CategoriesControllerIntTest {

    private Logger logger = LoggerFactory.getLogger(CategoriesControllerIntTest.class);;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    CategoryRepository categoryRepository;

    private Category initialCategory;

    @BeforeEach
    private void setUp() {
        initialCategory = new Category();
        initialCategory.setId(112L);
        initialCategory.setName("initial-category");

        Category testCategory = new Category();
        testCategory.setId(-1L);
        testCategory.setName("test-category");
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void createCategoryTest() throws Exception {
        mockMvc.perform(post("/category")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .param("id", "-1")
                .param("name", "test-category")
                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/categories"));
    }

    @WithMockUser(value = "admin", password = "admin", roles = {"ADMIN"})
    @Test
    public void adminDeleteCategoryTest() throws Exception {
        if (initialCategory == null) {
            logger.error("initialCategory is not initialized");
            return;
        } else {
            logger.info("initial category is initialized");
            logger.info(initialCategory.getId().toString());
        }
        Long id = categoryRepository.saveAndFlush(initialCategory).getId();
        logger.info(id.toString() + " - saved id");

        // id is autoincremented, not set manually
        mockMvc.perform(get("/category/1/delete")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf()))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name("categories"));
    }

}

