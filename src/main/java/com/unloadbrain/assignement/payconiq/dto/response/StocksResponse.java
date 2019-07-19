package com.unloadbrain.assignement.payconiq.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO class to return status if data collection process is successful or not as HTTP response.
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
