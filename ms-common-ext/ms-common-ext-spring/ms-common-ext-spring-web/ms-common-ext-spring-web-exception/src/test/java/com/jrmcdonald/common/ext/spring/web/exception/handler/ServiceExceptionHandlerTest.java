package com.jrmcdonald.common.ext.spring.web.exception.handler;

import com.jrmcdonald.common.ext.spring.web.exception.handler.app.ExceptionHandlerApplication;
import com.jrmcdonald.common.ext.spring.web.exception.handler.app.ExceptionHandlerController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = ExceptionHandlerApplication.class)
@AutoConfigureMockMvc
class ServiceExceptionHandlerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("Should handle NotFoundException")
    void shouldHandleNotFoundException() throws Exception {
        mockMvc.perform(post(ExceptionHandlerController.ENDPOINT + "/not-found")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should handle ConflictException")
    void shouldHandleConflictException() throws Exception {
        mockMvc.perform(post(ExceptionHandlerController.ENDPOINT + "/already-exists")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict());
    }

    @Test
    @DisplayName("Should handle ServiceException")
    void shouldHandleServiceException() throws Exception {
        mockMvc.perform(post(ExceptionHandlerController.ENDPOINT + "/server-error")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }
}