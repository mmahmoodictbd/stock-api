package com.unloadbrain.assignement.payconiq.stockapi.util;

import java.time.Instant;

public class DateTimeUtil {

    public long getCurrentTimeEpochMilli() {
        return Instant.now().toEpochMilli();
    }
}
