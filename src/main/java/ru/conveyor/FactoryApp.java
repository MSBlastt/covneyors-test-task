package ru.conveyor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@SpringBootApplication
public class FactoryApp {

    public static void main(String[] args) {
        SpringApplication.run(FactoryApp.class, args);
    }
}
