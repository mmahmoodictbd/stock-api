package com.unloadbrain.assignement.payconiq.domain.repository;

import com.unloadbrain.assignement.payconiq.domain.model.Stock;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface StockRepository extends CassandraRepository<Stock, String> {


}