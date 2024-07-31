package com.mycompany.webserverlenin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DeploymentController {

    private final DeploymentService deploymentService;

    @Autowired
    public DeploymentController(DeploymentService deploymentService) {
        this.deploymentService = deploymentService;
    }

    @GetMapping("/deploy/{jobCode}")
    public String deployJob(@PathVariable String jobCode) {
        boolean success = deploymentService.deployJobCode(jobCode);
        return success ? "Deployment successful" : "Deployment failed";
    }
    
    @GetMapping("/cancel/{jobCode}")
    public String cancelJob(@PathVariable String jobCode) {
        boolean success = deploymentService.cancelJobCode(jobCode);
        return success ? "Cancellation successful" : "Cancellation failed";
    }
}
