package com.mycompany.webserverlenin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JobCodeController {

    private final MangoDBConnection mangoDBConnection;

    @Autowired
    public JobCodeController(MangoDBConnection mangoDBConnection) {
        this.mangoDBConnection = mangoDBConnection;
    }

    @GetMapping("/generate-job-code")
    public String generateJobCode(@RequestParam String jobOrderType) {
        JOVar att = new JOVar();
        att.setJobOrderType(jobOrderType);
        return mangoDBConnection.jobCode(att);
    }
}
