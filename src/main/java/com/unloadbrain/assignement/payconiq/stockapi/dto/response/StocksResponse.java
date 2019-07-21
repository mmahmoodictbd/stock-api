package com.unloadbrain.assignement.payconiq.stockapi.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO class to return stocks as HTTP response.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StocksResponse {

    private List<StockResponse> data;
    private long pageNo;
    private long pageSize;
}
