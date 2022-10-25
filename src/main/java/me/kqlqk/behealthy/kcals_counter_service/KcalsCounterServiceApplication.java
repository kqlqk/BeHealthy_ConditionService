package me.kqlqk.behealthy.kcals_counter_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class KcalsCounterServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(KcalsCounterServiceApplication.class, args);
    }

}
