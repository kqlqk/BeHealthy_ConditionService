package me.kqlqk.behealthy.kcal_counter_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
public class KcalCounterServiceApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(KcalCounterServiceApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(KcalCounterServiceApplication.class);
    }
}
