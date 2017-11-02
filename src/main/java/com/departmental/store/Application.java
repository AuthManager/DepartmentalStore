package com.departmental.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;

@Configuration
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        System.out.println("Application started successfully");
    }

//    @Bean
//    public FilterRegistrationBean mdcClearingFilterRegistration() {
//        FilterRegistrationBean registration = new FilterRegistrationBean();
//        registration.setFilter(mdcClearingFilter());
//        registration.addUrlPatterns("/api/*");
//        registration.setName("MDCClearingFilter");
//        registration.setOrder(10);
//        return registration;
//    }
//
//    private Filter mdcClearingFilter() {
//        return new MDCClearingFilter();
//    }

}
