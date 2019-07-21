package com.unloadbrain.assignement.payconiq.stockapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.unloadbrain.assignement.payconiq.stockapi.api.StockAPI;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.CreateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.UpdateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.IdentityResponse;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.StockResponse;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.StocksResponse;
import com.unloadbrain.assignement.payconiq.stockapi.service.StockReactiveService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.concurrent.CompletableFuture;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@RunWith(SpringRunner.class)
@WebFluxTest
@ActiveProfiles("it")
public class StockAPIContractIT {

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StockReactiveService stockReactiveService;

    private WebTestClient webTestClient;

    @Before
    public void setup() {
        webTestClient = WebTestClient.bindToController(new StockAPI(stockReactiveService)).build();
    }

    @Test
    public void shouldCreateStock() throws Exception {

        when(stockReactiveService.createStock(any()))
                .thenReturn(CompletableFuture.supplyAsync(() -> IdentityResponse.builder().id("uuid").build()));

        CreateStockRequest createStockRequest =
                CreateStockRequest.builder().name("Apple").currentPrice(BigDecimal.TEN).build();

        webTestClient.post().uri("/api/stocks")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(objectMapper.writeValueAsString((createStockRequest)))
                .exchange()
                .expectStatus().isCreated()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .expectBody()
                .jsonPath("$.id").isEqualTo("uuid");

    }

    @Test
    public void shouldReturnStocks() {

        StockResponse stockResponse = StockResponse.builder()
                .id("uuid")
                .name("Apple")
                .currentPrice(BigDecimal.TEN)
                .lastUpdate(100)
                .build();

        when(stockReactiveService.getStocks(anyInt(), anyInt()))
                .thenReturn(CompletableFuture.supplyAsync(() ->
                        StocksResponse.builder()
                                .pageNo(0)
                                .pageSize(10)
                                .data(Collections.singletonList(stockResponse))
                                .build()
                ));

        webTestClient.get().uri("/api/stocks")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .expectBody()
                .jsonPath("$.pageNo").isEqualTo(0)
                .jsonPath("$.pageSize").isEqualTo(10)
                .jsonPath("$.data[0].id").isEqualTo("uuid")
                .jsonPath("$.data[0].name").isEqualTo("Apple")
                .jsonPath("$.data[0].currentPrice").isEqualTo(10.0)
                .jsonPath("$.data[0].lastUpdate").isEqualTo(100);

    }

    @Test
    public void shouldReturnStockById() {

        when(stockReactiveService.getStock(anyString()))
                .thenReturn(CompletableFuture.supplyAsync(() ->
                        StockResponse.builder()
                                .id("uuid")
                                .name("Apple")
                                .currentPrice(BigDecimal.TEN)
                                .lastUpdate(100)
                                .build()
                ));

        webTestClient.get().uri("/api/stocks/uuid")
                .accept(MediaType.APPLICATION_JSON_UTF8)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .expectBody()
                .jsonPath("$.id").isEqualTo("uuid")
                .jsonPath("$.name").isEqualTo("Apple")
                .jsonPath("$.currentPrice").isEqualTo(10.0)
                .jsonPath("$.lastUpdate").isEqualTo(100);

    }

    @Test
    public void shouldUpdateStock() throws Exception {

        when(stockReactiveService.updateStock(any()))
                .thenReturn(CompletableFuture.supplyAsync(() ->
                        StockResponse.builder()
                                .id("uuid")
                                .name("Apple")
                                .currentPrice(BigDecimal.TEN)
                                .lastUpdate(100)
                                .build()
                ));

        UpdateStockRequest updateStockRequest =
                UpdateStockRequest.builder().id("uuid").name("Apple").currentPrice(BigDecimal.TEN).build();

        webTestClient.put().uri("/api/stocks/uuid")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .syncBody(objectMapper.writeValueAsString((updateStockRequest)))
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .expectBody()
                .jsonPath("$.id").isEqualTo("uuid")
                .jsonPath("$.name").isEqualTo("Apple")
                .jsonPath("$.currentPrice").isEqualTo(10.0)
                .jsonPath("$.lastUpdate").isEqualTo(100);


    }
}