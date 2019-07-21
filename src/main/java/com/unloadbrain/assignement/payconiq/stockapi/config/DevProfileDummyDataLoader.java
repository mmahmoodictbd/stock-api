package com.unloadbrain.assignement.payconiq.stockapi.config;

import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.CreateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.service.StockPersistenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Save dummy stock on load for dev profile.
 */

@Slf4j
@Profile("dev")
@Component
@AllArgsConstructor
public class DevProfileDummyDataLoader implements ApplicationRunner {

    private StockPersistenceService stockPersistenceService;

    public void run(ApplicationArguments args) {

        log.info("Add dummy stock data to the database.");

        List<CreateStockRequest> requests = new ArrayList<>();

        CreateStockRequest createStockRequest =
                CreateStockRequest.builder().name("Apple").currentPrice(BigDecimal.TEN).build();
        requests.add(createStockRequest);

        createStockRequest = CreateStockRequest.builder().name("Google").currentPrice(BigDecimal.valueOf(5)).build();
        requests.add(createStockRequest);

        createStockRequest = CreateStockRequest.builder().name("Microsoft").currentPrice(BigDecimal.valueOf(4)).build();
        requests.add(createStockRequest);

        createStockRequest = CreateStockRequest.builder().name("Redhat").currentPrice(BigDecimal.valueOf(11)).build();
        requests.add(createStockRequest);

        requests.stream().forEach(request -> stockPersistenceService.createStock(request));
    }
}