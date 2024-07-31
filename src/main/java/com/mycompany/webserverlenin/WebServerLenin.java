/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.webserverlenin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author khanny
 */
@SpringBootApplication
@EnableScheduling
public class WebServerLenin {

    public static void main(String[] args) {
        SpringApplication.run(WebServerLenin.class, args);
    }
}


