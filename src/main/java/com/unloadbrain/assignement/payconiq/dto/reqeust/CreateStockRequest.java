package com.unloadbrain.assignement.payconiq.dto.reqeust;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateStockRequest {

    @NotNull
    @Pattern(regexp = "^\\w+$", message = "Name has to be alphanumeric.}")
    private String name;

    @NotNull
    @Digits(integer = 12, fraction = 2, message = "Current price has a max limit of 12 integer and 2 fraction digit.")
    @DecimalMin(value = "0", message = "Current price has to be positive.")
    private BigDecimal currentPrice;
}
