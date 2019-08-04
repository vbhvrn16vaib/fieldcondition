package com.coding.fieldcondition.controller;

import com.coding.fieldcondition.FieldConditionApplication;
import org.hamcrest.core.Is;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = FieldConditionApplication.class)
@AutoConfigureMockMvc
public class FieldConditionControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    public void getFieldStatistics() throws Exception {
        mvc.perform(get("/field-statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vegetation.min").exists())
                .andExpect(jsonPath("$.vegetation.max").exists())
                .andExpect(jsonPath("$.vegetation.avg").exists());
    }

    @Test
    public void captureFieldCondition() throws Exception {
        mvc.perform(post("/field-conditions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\n" +
                        "    \"vegetation\" : 0.45,\n" +
                        "    \"occurrenceAt\" : \"2019-07-23T08:50Z\"\n" +
                        "}"))
                .andExpect(status().isCreated());

        mvc.perform(get("/field-statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.vegetation.min").value(Is.is(0.45)))
                .andExpect(jsonPath("$.vegetation.max").value(Is.is(0.45)))
                .andExpect(jsonPath("$.vegetation.avg").value(Is.is(0.45)));
    }

    @Test
    public void captureFieldConditionEmptyInput() throws Exception {
        mvc.perform(post("/field-conditions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void captureFieldConditionNullInputs() throws Exception {
        mvc.perform(post("/field-conditions")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{}"))
                .andExpect(status().is4xxClientError());
    }
}