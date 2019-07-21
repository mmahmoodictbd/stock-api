package com.unloadbrain.assignement.payconiq.stockapi.service;

import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.testkit.TestActorRef;
import akka.testkit.TestKit;
import com.unloadbrain.assignement.payconiq.stockapi.akka.StockActor;
import com.unloadbrain.assignement.payconiq.stockapi.akka.StockActorFactory;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.CreateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.UpdateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.IdentityResponse;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.StockResponse;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.StocksResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import scala.concurrent.duration.Duration;

import java.math.BigDecimal;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class StockReactiveServiceTest {

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
    public void shouldReturnCompletableFutureWhenInvokeGetStocks() throws Exception {

        new TestKit(actorSystem) {{

            // Given

            StocksResponse stocksResponse = mock(StocksResponse.class);
            StockPersistenceService stockPersistenceServiceMock = mock(StockPersistenceService.class);
            when(stockPersistenceServiceMock.getStocks(anyInt(), anyInt())).thenReturn(stocksResponse);

            TestActorRef<StockActor> stockActor =
                    TestActorRef.create(actorSystem, Props.create(StockActor.class, stockPersistenceServiceMock));
            StockActorFactory stockActorFactoryMock = mock(StockActorFactory.class);
            when(stockActorFactoryMock.getObject()).thenReturn(stockActor);

            // When
            StockReactiveService stockReactiveService = new StockReactiveService(stockActorFactoryMock, 10000L);

            // When
            CompletableFuture<Object> future = stockReactiveService.getStocks(0, 10);

            // Then
            assertNotNull(future);
            assertTrue(future.get() instanceof StocksResponse);

        }};

    }

    @Test
    public void shouldReturnCompletableFutureWhenInvokeGetStock() throws Exception {

        new TestKit(actorSystem) {{

            // Given

            StockResponse stockResponse = mock(StockResponse.class);
            StockPersistenceService stockPersistenceServiceMock = mock(StockPersistenceService.class);
            when(stockPersistenceServiceMock.getStock(anyString())).thenReturn(stockResponse);

            TestActorRef<StockActor> stockActor =
                    TestActorRef.create(actorSystem, Props.create(StockActor.class, stockPersistenceServiceMock));
            StockActorFactory stockActorFactoryMock = mock(StockActorFactory.class);
            when(stockActorFactoryMock.getObject()).thenReturn(stockActor);

            // When
            StockReactiveService stockReactiveService = new StockReactiveService(stockActorFactoryMock, 10000L);

            // When
            CompletableFuture<Object> future = stockReactiveService.getStock("uuid");

            // Then
            assertNotNull(future);
            assertTrue(future.get() instanceof StockResponse);

        }};
    }

    @Test
    public void shouldReturnCompletableFutureWhenInvokeCreateStock() throws Exception {

        new TestKit(actorSystem) {{

            // Given

            IdentityResponse identityResponse = mock(IdentityResponse.class);
            StockPersistenceService stockPersistenceServiceMock = mock(StockPersistenceService.class);
            when(stockPersistenceServiceMock.createStock(any())).thenReturn(identityResponse);

            TestActorRef<StockActor> stockActor =
                    TestActorRef.create(actorSystem, Props.create(StockActor.class, stockPersistenceServiceMock));
            StockActorFactory stockActorFactoryMock = mock(StockActorFactory.class);
            when(stockActorFactoryMock.getObject()).thenReturn(stockActor);

            // When
            StockReactiveService stockReactiveService = new StockReactiveService(stockActorFactoryMock, 10000L);

            // When
            CreateStockRequest createStockRequest = CreateStockRequest.builder()
                    .name("Apple")
                    .currentPrice(BigDecimal.TEN)
                    .build();
            CompletableFuture<Object> future = stockReactiveService.createStock(createStockRequest);

            // Then
            assertNotNull(future);
            assertTrue(future.get() instanceof IdentityResponse);

        }};
    }

    @Test
    public void shouldReturnCompletableFutureWhenInvokeUpdateStock() throws Exception {

        new TestKit(actorSystem) {{

            // Given

            StockResponse stockResponse = mock(StockResponse.class);
            StockPersistenceService stockPersistenceServiceMock = mock(StockPersistenceService.class);
            when(stockPersistenceServiceMock.updateStock(any())).thenReturn(stockResponse);

            TestActorRef<StockActor> stockActor =
                    TestActorRef.create(actorSystem, Props.create(StockActor.class, stockPersistenceServiceMock));
            StockActorFactory stockActorFactoryMock = mock(StockActorFactory.class);
            when(stockActorFactoryMock.getObject()).thenReturn(stockActor);

            // When
            StockReactiveService stockReactiveService = new StockReactiveService(stockActorFactoryMock, 10000L);

            // When
            UpdateStockRequest updateStockRequest = UpdateStockRequest.builder()
                    .id("uuid")
                    .name("Apple")
                    .currentPrice(BigDecimal.TEN)
                    .build();
            CompletableFuture<Object> future = stockReactiveService.updateStock(updateStockRequest);

            // Then
            assertNotNull(future);
            assertTrue(future.get() instanceof StockResponse);

        }};
    }
}