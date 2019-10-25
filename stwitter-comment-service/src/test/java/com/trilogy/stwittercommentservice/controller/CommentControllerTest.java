//package com.trilogy.stwittercommentservice.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.trilogy.stwittercommentservice.model.Comment;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import java.time.LocalDate;
//
//import static org.hamcrest.Matchers.containsString;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest
//public class CommentControllerTest {
//
//    @Autowired
//    MockMvc mockMvc;
//
//    @Autowired
//    private ObjectMapper objectMapper;
//
//    @Test
//    public void shouldReturnDefaultMessage() throws Exception {
//        this.mockMvc.perform(get("/comment")).andDo(print()).andExpect(status().isOk())
//                .andExpect(content().string(containsString("This is the comment")));
//
//    }
//
//    @Test
//    public void shouldReturnOkWithValidRequestBody() throws Exception {
//        Comment comment = new Comment(
//                1,
//                "Arturo",
//                LocalDate.of(2019,07,13),
//                "This is a comment made from the createGetDelete test");
//
//        this.mockMvc.perform(post("/comment")
//                .contentType("application/json")
//                .content(objectMapper.writeValueAsString(comment)))
//                .andExpect(status().isCreated());
//    }
//
//}
