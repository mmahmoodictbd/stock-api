package com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateStockRequest {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private BigDecimal currentPrice;
}
