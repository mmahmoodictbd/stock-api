package com.unloadbrain.assignement.payconiq.stockapi.api;

import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.CreateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.dto.reqeust.UpdateStockRequest;
import com.unloadbrain.assignement.payconiq.stockapi.service.StockReactiveService;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import javax.validation.Valid;


/**
 * This class provide API endpoints for Stocks.
 */
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/stocks")
public class StockAPI {

    private StockReactiveService stockReactiveService;

    @ApiOperation(value = "Return the stocks")
    @GetMapping
    public Mono<Object> getStocks(@RequestParam(value = "pageNo", defaultValue = "0") int pageNo,
                                  @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {

        return Mono.fromFuture(stockReactiveService.getStocks(pageNo, pageSize));
    }

    @ApiOperation(value = "Return stock by id")
    @GetMapping("/{id}")
    public Mono<Object> getStock(@PathVariable final String id) {

        return Mono.fromFuture(stockReactiveService.getStock(id));
    }

    @ApiOperation(value = "Create stock")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Object> createStock(@Valid @RequestBody final CreateStockRequest stock) {

        return Mono.fromFuture(stockReactiveService.createStock(stock));
    }

    @ApiOperation(value = "Update existing stock")
    @PutMapping(value = "/{id}")
    public Mono<Object> updateStock(@PathVariable final String id,
                                    @Valid @RequestBody final UpdateStockRequest updateStockRequest) {

        updateStockRequest.setId(id);
        return Mono.fromFuture(stockReactiveService.updateStock(updateStockRequest));
    }


}
