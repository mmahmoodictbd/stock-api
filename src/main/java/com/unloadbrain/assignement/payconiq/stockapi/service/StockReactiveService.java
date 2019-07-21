package com.unloadbrain.assignement.payconiq.stockapi.service;

import akka.actor.ActorRef;
import akka.pattern.Patterns;
import com.unloadbrain.assignement.payconiq.stockapi.akka.StockActorFactory;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.CreateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.GetStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.GetStocksRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.UpdateStockRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import scala.compat.java8.FutureConverters;
import scala.concurrent.Future;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class StockReactiveService {

    private StockActorFactory stockActorFactory;
    private long timeoutInMillis;

    public StockReactiveService(StockActorFactory stockActorFactory,
                                @Value("${app.akka.ask-timeout-in-millis}") long timeoutInMillis) {
        this.stockActorFactory = stockActorFactory;
        this.timeoutInMillis = timeoutInMillis;
    }

    public CompletableFuture<Object> getStocks(int pageNo, int pageSize) {

        GetStocksRequest request = GetStocksRequest.builder()
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();

        Future<Object> result = getObjectFuture(request);

        return (CompletableFuture<Object>) FutureConverters.toJava(result);
    }

    public CompletableFuture<Object> getStock(String id) {

        GetStockRequest request = GetStockRequest.builder().id(id).build();

        Future<Object> result = getObjectFuture(request);

        return (CompletableFuture<Object>) FutureConverters.toJava(result);
    }

    public CompletableFuture<Object> updateStock(UpdateStockRequest updateStockRequest) {

        Future<Object> result = getObjectFuture(updateStockRequest);

        return (CompletableFuture<Object>) FutureConverters.toJava(result);
    }

    public CompletableFuture<Object> createStock(CreateStockRequest createStockRequest) {

        Future<Object> result = getObjectFuture(createStockRequest);

        return (CompletableFuture<Object>) FutureConverters.toJava(result);
    }

    private Future<Object> getObjectFuture(Object request) {
        ActorRef actorRef = stockActorFactory.getObject();
        return Patterns.ask(actorRef, request, timeoutInMillis);
    }
}
