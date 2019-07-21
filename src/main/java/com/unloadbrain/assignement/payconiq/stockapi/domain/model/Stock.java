package com.unloadbrain.assignement.payconiq.stockapi.domain.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.math.BigDecimal;

@Data
@Builder
@Table
public class Stock {

    @PrimaryKey
    private String id;

    private String name;

    private BigDecimal currentPrice;

    private long lastUpdate;
}