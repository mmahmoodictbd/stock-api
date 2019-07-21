package com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO class to read JSON HTTP request body.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetStocksRequest {

    private int pageNo;
    private int pageSize;
}
