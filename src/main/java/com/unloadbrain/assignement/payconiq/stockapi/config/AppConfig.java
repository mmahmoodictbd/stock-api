package com.unloadbrain.assignement.payconiq.stockapi.config;

import com.unloadbrain.assignement.payconiq.stockapi.util.DateTimeUtil;
import com.unloadbrain.assignement.payconiq.stockapi.util.UuidUtil;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Provides application related beans.
 */
@Configuration
@AllArgsConstructor
public class AppConfig {

    @Bean
    public UuidUtil uuidUtil() {
        return new UuidUtil();
    }

    @Bean
    public DateTimeUtil dateTimeUtil() {
        return new DateTimeUtil();
    }

}
