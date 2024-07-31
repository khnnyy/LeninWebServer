package com.mycompany.webserverlenin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class FormController {

    @GetMapping("/job-order-form")
    public String showJobOrderForm(Model model) {
        model.addAttribute("jobOrderForm", new JobOrderForm());
        return "jobOrderForm";
    }

    @PostMapping("/submit-job-order")
    public String submitJobOrder(@ModelAttribute JobOrderForm jobOrderForm, Model model) {
        // Print form data to the console
        System.out.println("Date Issued: " + jobOrderForm.getDateIssued());
        System.out.println("Job Code: " + jobOrderForm.getJobCode());
        System.out.println("Job Order Type: " + jobOrderForm.getJobOrderType());
        System.out.println("Job Code Text: " + jobOrderForm.getJobCodeText());
        System.out.println("Client Name: " + jobOrderForm.getClientName());
        System.out.println("Contact: " + jobOrderForm.getContact());
        System.out.println("Request Recommendation: " + jobOrderForm.getRequestRecommendation());
        System.out.println("Address: " + jobOrderForm.getAddress());
        System.out.println("Date Deployed: " + jobOrderForm.getDateDeployed());
        System.out.println("Service Request: " + jobOrderForm.getServiceRequest());
        System.out.println("Leader: " + jobOrderForm.getLeader());
        System.out.println("Date Due: " + jobOrderForm.getDateDue());
        System.out.println("Man Power: " + jobOrderForm.getManPower());
        System.out.println("Instructions: " + jobOrderForm.getInstructions());

        // Add form data to the model to display in the result view
        model.addAttribute("jobOrderForm", jobOrderForm);

        // Return the name of the view to render the result
        return "result"; // Name of the result view
    }
}
