/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webserverlenin;

/**
 *
 * @author khanny
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class KeepAliveController {
    
    @Autowired
    private SecurityProperties securityProperties;

    @GetMapping("/health")
    public String healthCheck() {
//        securityProperties.getUser().setUsername("khan");
//        securityProperties.getUser().setPassword("pot");
        return "Application is up and running!";
    }
    
}