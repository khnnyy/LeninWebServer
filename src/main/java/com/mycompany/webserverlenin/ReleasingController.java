package com.mycompany.webserverlenin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author khanny
 */
public class ReleasingController {
    private final ReleasingService releasingService;

    @Autowired
    public ReleasingController(ReleasingService releasingService) {
        this.releasingService = releasingService;
    }

    @GetMapping("/deploy/{jobCode}")
    public String deployJob(@PathVariable String jobCode) {
        boolean success = releasingService.releaseJobCode(jobCode);
        return success ? "Release successful" : "Deployment failed";
    }
    
}
