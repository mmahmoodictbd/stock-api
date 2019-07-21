package com.unloadbrain.assignement.payconiq.stockapi.domain.repository;

import com.unloadbrain.assignement.payconiq.stockapi.domain.model.Stock;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface StockRepository extends CassandraRepository<Stock, String> {


}