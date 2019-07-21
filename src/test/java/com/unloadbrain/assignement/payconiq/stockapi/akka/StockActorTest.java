package com.unloadbrain.assignement.payconiq.stockapi.akka;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.testkit.TestKit;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.CreateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.GetStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.GetStocksRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.UpdateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.IdentityResponse;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.StockResponse;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.StocksResponse;
import com.unloadbrain.assignement.payconiq.stockapi.service.StockPersistenceService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import scala.concurrent.duration.Duration;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StockActorTest {

    private ActorSystem actorSystem;

    @Before
    public void setup() {
        actorSystem = ActorSystem.create();
    }

    @After
    public void teardown() {
        Duration duration = Duration.create(10L, TimeUnit.SECONDS);
        TestKit.shutdownActorSystem(actorSystem, duration, true);
        actorSystem = null;
    }

    @Test
    public void getStocksShouldInvokedWhenMessageTypeIsGetStocksRequest() {

        new TestKit(actorSystem) {{

            // Given

            StockResponse stockResponse = StockResponse.builder()
                    .id("uuid")
                    .name("Apple")
                    .currentPrice(BigDecimal.TEN)
                    .lastUpdate(100L)
                    .build();

            StocksResponse stocksResponse = StocksResponse.builder()
                    .pageNo(0)
                    .pageSize(10)
                    .data(Collections.singletonList(stockResponse))
                    .build();

            StockPersistenceService stockPersistenceServiceMock = mock(StockPersistenceService.class);
            when(stockPersistenceServiceMock.getStocks(anyInt(), anyInt())).thenReturn(stocksResponse);

            TestActorRef<StockActor> stockActor =
                    TestActorRef.create(actorSystem, Props.create(StockActor.class, stockPersistenceServiceMock));

            // When
            GetStocksRequest stocksRequest = GetStocksRequest.builder().pageNo(0).pageSize(10).build();
            stockActor.tell(stocksRequest, testActor());

            // Then
            expectMsg(stocksResponse);
            verify(stockPersistenceServiceMock, times(1)).getStocks(0, 10);

        }};
    }

    @Test
    public void getStockShouldInvokedWhenMessageTypeIsGetStockRequest() throws InterruptedException {

        new TestKit(actorSystem) {{

            // Given

            StockResponse stockResponse = StockResponse.builder()
                    .id("uuid")
                    .name("Apple")
                    .currentPrice(BigDecimal.TEN)
                    .lastUpdate(100L)
                    .build();

            StockPersistenceService stockPersistenceServiceMock = mock(StockPersistenceService.class);
            when(stockPersistenceServiceMock.getStock(anyString())).thenReturn(stockResponse);

            TestActorRef<StockActor> stockActor =
                    TestActorRef.create(actorSystem, Props.create(StockActor.class, stockPersistenceServiceMock));

            // When
            GetStockRequest stockRequest = GetStockRequest.builder().id("uuid").build();
            stockActor.tell(stockRequest, testActor());

            // Then
            expectMsg(stockResponse);
            verify(stockPersistenceServiceMock, times(1)).getStock("uuid");

        }};
    }

    @Test
    public void createStockShouldInvokedWhenMessageTypeIsCreateStockRequest() {

        new TestKit(actorSystem) {{

            // Given

            IdentityResponse identityResponse = IdentityResponse.builder().id("uuid").build();

            StockPersistenceService stockPersistenceServiceMock = mock(StockPersistenceService.class);
            when(stockPersistenceServiceMock.createStock(any())).thenReturn(identityResponse);

            TestActorRef<StockActor> stockActor =
                    TestActorRef.create(actorSystem, Props.create(StockActor.class, stockPersistenceServiceMock));

            // When
            CreateStockRequest createStockRequest = CreateStockRequest.builder()
                    .name("Apple")
                    .currentPrice(BigDecimal.TEN)
                    .build();
            stockActor.tell(createStockRequest, testActor());

            // Then
            expectMsg(identityResponse);
            verify(stockPersistenceServiceMock, times(1)).createStock(createStockRequest);

        }};
    }

    @Test
    public void updateStockShouldInvokedWhenMessageTypeIsUpdateStockRequest() {

        new TestKit(actorSystem) {{

            // Given

            StockResponse stockResponse = StockResponse.builder()
                    .id("uuid")
                    .name("Apple")
                    .currentPrice(BigDecimal.TEN)
                    .lastUpdate(100L)
                    .build();

            StockPersistenceService stockPersistenceServiceMock = mock(StockPersistenceService.class);
            when(stockPersistenceServiceMock.updateStock(any())).thenReturn(stockResponse);

            TestActorRef<StockActor> stockActor =
                    TestActorRef.create(actorSystem, Props.create(StockActor.class, stockPersistenceServiceMock));

            // When
            UpdateStockRequest updateStockRequest = UpdateStockRequest.builder()
                    .id("uuid")
                    .name("Apple")
                    .currentPrice(BigDecimal.TEN)
                    .build();
            stockActor.tell(updateStockRequest, testActor());

            // Then
            expectMsg(stockResponse);
            verify(stockPersistenceServiceMock, times(1)).updateStock(updateStockRequest);

        }};
    }
}