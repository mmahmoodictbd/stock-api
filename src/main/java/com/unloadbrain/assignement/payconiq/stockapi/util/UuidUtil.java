package com.unloadbrain.assignement.payconiq.stockapi.util;

import java.util.UUID;

/**
 * Provides UUID related APIs.
 * Mocking static method is awful with Powermock, lets use this.
 */
public class UuidUtil {

    /**
     * Gets random uuid as string.
     *
     * @return random uuid.
     */
    public String getRandomUuid() {
        return UUID.randomUUID().toString();
    }
}
