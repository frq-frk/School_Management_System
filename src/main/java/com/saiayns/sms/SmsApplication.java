package com.saiayns.sms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.saiayns.sms.config.DynamicRoutingDataSource;

@SpringBootApplication
public class SmsApplication implements CommandLineRunner{

	@Autowired
    private DynamicRoutingDataSource dynamicRoutingDataSource;
	
	public static void main(String[] args) {
		SpringApplication.run(SmsApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		dynamicRoutingDataSource.initializeTenants();
	}

}
