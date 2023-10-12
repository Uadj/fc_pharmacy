package com.example.fc_drug;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class FcDrugApplication {

    public static void main(String[] args) {
        SpringApplication.run(FcDrugApplication.class, args);
    }

}
