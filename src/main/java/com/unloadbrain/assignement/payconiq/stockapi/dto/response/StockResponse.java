package com.unloadbrain.assignement.payconiq.stockapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO class to return stock data as HTTP response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockResponse {

    private String id;
    private String name;
    private BigDecimal currentPrice;
    private long lastUpdate;
}
