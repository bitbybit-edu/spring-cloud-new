package com.bitbybit.consumer.conroller;

import com.bitbybit.consumer.remote.api.ProviderApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ConsumerController {
    @Autowired
    ProviderApi providerApi;

	@GetMapping("consumer/ratelimiter")
	public Integer ratelimiter() {

        return providerApi.ratelimiter();
	}

    @GetMapping("consumer/bulkhead")
    public Integer bulkhead() {

        return providerApi.bulkhead();
    }



}
