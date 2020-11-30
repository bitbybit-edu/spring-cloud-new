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

    AtomicInteger atomicInteger = new AtomicInteger();

	@GetMapping("consumer/consumer")
	public Integer consumer() {
		System.out.println(atomicInteger.incrementAndGet());
        return providerApi.provider();
	}
}
