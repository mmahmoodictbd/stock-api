package com.unloadbrain.assignement.payconiq.util;

import java.time.Instant;

public class DateTimeUtil {

    public long getCurrentTimeEpochMilli() {
        return Instant.now().toEpochMilli();
    }
}
