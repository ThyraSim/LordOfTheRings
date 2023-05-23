package com.example.lordoftherings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LordOfTheRingsApplication {

    public static void main(String[] args) {
        System.out.println("Je suis un chat");
        SpringApplication.run(LordOfTheRingsApplication.class, args);
    }
}
