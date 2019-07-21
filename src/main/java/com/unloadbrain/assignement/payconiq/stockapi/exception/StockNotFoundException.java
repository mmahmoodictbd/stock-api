package com.unloadbrain.assignement.payconiq.stockapi.exception;


public class StockNotFoundException extends RuntimeException {

    public StockNotFoundException(String message) {
        super(message);
    }
}
