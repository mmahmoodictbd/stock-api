package com.unloadbrain.assignement.payconiq.stockapi.service;

import com.unloadbrain.assignement.payconiq.stockapi.domain.model.Stock;
import com.unloadbrain.assignement.payconiq.stockapi.domain.repository.StockRepository;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.CreateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.UpdateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.IdentityResponse;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.StockResponse;
import com.unloadbrain.assignement.payconiq.stockapi.dto.response.StocksResponse;
import com.unloadbrain.assignement.payconiq.stockapi.exception.StockNotFoundException;
import com.unloadbrain.assignement.payconiq.stockapi.util.DateTimeUtil;
import com.unloadbrain.assignement.payconiq.stockapi.util.UuidUtil;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Slice;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StockPersistenceServiceTest {

    private final StockRepository stockRepositoryMock;
    private final UuidUtil uuidUtilMock;
    private final DateTimeUtil dateTimeUtilMock;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private StockPersistenceService stockPersistenceService;

    public StockPersistenceServiceTest() {
        stockRepositoryMock = mock(StockRepository.class);
        uuidUtilMock = mock(UuidUtil.class);
        dateTimeUtilMock = mock(DateTimeUtil.class);
        stockPersistenceService = new StockPersistenceService(stockRepositoryMock, uuidUtilMock, dateTimeUtilMock);
    }

    @Test
    public void shouldReturnStocksPageableResponse() {

        // Given

        Stock stock = Stock.builder()
                .id("700247b2-26e0-4229-b8ae-f7c1e2ea78e7")
                .name("Apple")
                .currentPrice(BigDecimal.ONE)
                .lastUpdate(100L)
                .build();

        Slice<Stock> sliceMock = mock(Slice.class);
        when(sliceMock.hasNext()).thenReturn(true);
        when(sliceMock.getContent()).thenReturn(Collections.singletonList(stock));

        when(stockRepositoryMock.findAll(any())).thenReturn(sliceMock);

        // When
        StocksResponse stocksResponse = stockPersistenceService.getStocks(1, 10);

        // Then
        assertEquals(1, stocksResponse.getPageNo());
        assertEquals(10, stocksResponse.getPageSize());
        assertEquals("700247b2-26e0-4229-b8ae-f7c1e2ea78e7", stocksResponse.getData().get(0).getId());
        assertEquals("Apple", stocksResponse.getData().get(0).getName());
        assertEquals(BigDecimal.ONE, stocksResponse.getData().get(0).getCurrentPrice());
        assertEquals(100L, stocksResponse.getData().get(0).getLastUpdate());
    }

    @Test
    public void shouldReturnStockIfExist() {

        // Given

        Stock existingStock = Stock.builder()
                .id("700247b2-26e0-4229-b8ae-f7c1e2ea78e7")
                .name("Apple")
                .currentPrice(BigDecimal.ONE)
                .lastUpdate(100L)
                .build();
        when(stockRepositoryMock.findById(any(String.class))).thenReturn(Optional.of(existingStock));

        // When
        StockResponse stockResponse = stockPersistenceService.getStock("700247b2-26e0-4229-b8ae-f7c1e2ea78e7");

        // Then
        assertEquals("700247b2-26e0-4229-b8ae-f7c1e2ea78e7", stockResponse.getId());
        assertEquals("Apple", stockResponse.getName());
        assertEquals(BigDecimal.ONE, stockResponse.getCurrentPrice());
        assertEquals(100L, stockResponse.getLastUpdate());
    }

    @Test
    public void shouldThrowExceptionWhenReturnStockIfNotExist() {

        // Given

        when(stockRepositoryMock.findById(any(String.class))).thenReturn(Optional.empty());

        thrown.expect(StockNotFoundException.class);
        thrown.expectMessage("Stock [id: unknown] not found.");

        // When
        stockPersistenceService.getStock("unknown");

        // Then
        // Expect test to be passed.
    }

    @Test
    public void shouldReturnUuidWhenCreateStock() {

        // Given

        ArgumentCaptor<Stock> stockArgumentCaptor = ArgumentCaptor.forClass(Stock.class);

        CreateStockRequest createStockRequest = CreateStockRequest.builder()
                .name("Apple")
                .currentPrice(BigDecimal.TEN)
                .build();

        when(uuidUtilMock.getRandomUuid()).thenReturn("700247b2-26e0-4229-b8ae-f7c1e2ea78e7");
        when(dateTimeUtilMock.getCurrentTimeEpochMilli()).thenReturn(100L);

        Stock stockAny = mock(Stock.class);
        when(stockRepositoryMock.save(any(Stock.class))).thenReturn(stockAny);

        // When
        IdentityResponse identityResponse = stockPersistenceService.createStock(createStockRequest);

        // Then

        assertEquals("700247b2-26e0-4229-b8ae-f7c1e2ea78e7", identityResponse.getId());

        verify(stockRepositoryMock).save(stockArgumentCaptor.capture());
        assertEquals("700247b2-26e0-4229-b8ae-f7c1e2ea78e7", stockArgumentCaptor.getValue().getId());
        assertEquals("Apple", stockArgumentCaptor.getValue().getName());
        assertEquals(BigDecimal.TEN, stockArgumentCaptor.getValue().getCurrentPrice());
        assertEquals(100L, stockArgumentCaptor.getValue().getLastUpdate());

    }

    @Test
    public void shouldUpdateStockIfExist() {

        // Given

        UpdateStockRequest updateStockRequest = UpdateStockRequest.builder()
                .id("700247b2-26e0-4229-b8ae-f7c1e2ea78e7")
                .name("Apple Inc.")
                .currentPrice(BigDecimal.TEN)
                .build();

        Stock existingStock = Stock.builder()
                .id("700247b2-26e0-4229-b8ae-f7c1e2ea78e7")
                .name("Apple")
                .currentPrice(BigDecimal.ONE)
                .lastUpdate(100L)
                .build();
        when(stockRepositoryMock.findById(any(String.class))).thenReturn(Optional.of(existingStock));

        when(dateTimeUtilMock.getCurrentTimeEpochMilli()).thenReturn(999L);

        // When
        StockResponse stockResponse = stockPersistenceService.updateStock(updateStockRequest);


        // Then
        assertEquals("700247b2-26e0-4229-b8ae-f7c1e2ea78e7", stockResponse.getId());
        assertEquals("Apple Inc.", stockResponse.getName());
        assertEquals(BigDecimal.TEN, stockResponse.getCurrentPrice());
        assertEquals(999L, stockResponse.getLastUpdate());
    }

    @Test
    public void shouldThrowExceptionWhenUpdateStockIfNotExist() {

        // Given

        UpdateStockRequest updateStockRequest = UpdateStockRequest.builder()
                .id("unknown")
                .name("Apple Inc.")
                .currentPrice(BigDecimal.TEN)
                .build();

        when(stockRepositoryMock.findById(any(String.class))).thenReturn(Optional.empty());

        thrown.expect(StockNotFoundException.class);
        thrown.expectMessage("Stock [id: unknown] not found.");

        // When
        stockPersistenceService.updateStock(updateStockRequest);

        // Then
        // Expect test to be passed.
    }
}