package com.unloadbrain.assignement.payconiq.stockapi.exception;

import com.unloadbrain.assignement.payconiq.stockapi.Application;
import com.unloadbrain.assignement.payconiq.stockapi.CassandraTestConfig;
import com.unloadbrain.assignement.payconiq.stockapi.service.StockReactiveService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class})
@AutoConfigureMockMvc
@Import(CassandraTestConfig.class)
@ActiveProfiles("it")
public class ApplicationExceptionHandlerIT {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StockReactiveService stockReactiveService;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    /**
     * This test ensure internal exception stacktraces are not spilling over HTTP response.
     */
    @Test
    public void runtimeExceptionShouldBeCaughtByGlobalExceptionHandler() throws Exception {

        doThrow(new RuntimeException("Something bad")).when(stockReactiveService).createStock(any());

        mockMvc.perform(post("/api/stocks"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.code").value("INTERNAL_SERVER_ERROR"))
                .andExpect(jsonPath("$.message").value("An unexpected internal server error occurred"))
                .andDo(print());
    }
}