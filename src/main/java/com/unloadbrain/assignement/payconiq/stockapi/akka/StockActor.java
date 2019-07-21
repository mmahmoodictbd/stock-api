package com.unloadbrain.assignement.payconiq.stockapi.akka;

import akka.actor.AbstractActor;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.CreateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.GetStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.GetStocksRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.UpdateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.service.StockPersistenceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Scope("prototype")
@AllArgsConstructor
public class StockActor extends AbstractActor {

    private StockPersistenceService stockPersistenceService;

    @Override
    public Receive createReceive() {

        return receiveBuilder()
                .match(GetStocksRequest.class, message -> handleGetStocks(message))
                .match(GetStockRequest.class, message -> handleGetStock(message))
                .match(CreateStockRequest.class, message -> handleCreateStockRequest(message))
                .match(UpdateStockRequest.class, message -> handleUpdateStockRequest(message))
                .matchAny(message -> {
                    log.info("Unknown message received: {}", message);
                    unhandled(message);
                })
                .build();
    }

    private void handleGetStocks(GetStocksRequest request) {
        getSender().tell(stockPersistenceService.getStocks(request.getPageNo(), request.getPageSize()), getSelf());
    }

    private void handleGetStock(GetStockRequest request) {
        getSender().tell(stockPersistenceService.getStock(request.getId()), getSelf());
    }

    private void handleCreateStockRequest(CreateStockRequest request) {
        getSender().tell(stockPersistenceService.createStock(request), getSelf());
    }

    private void handleUpdateStockRequest(UpdateStockRequest request) {
        getSender().tell(stockPersistenceService.updateStock(request), getSelf());
    }
}