package com.mycompany.webserverlenin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.MessagingException;
import javax.swing.*;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class FormController {

    private final MangoDBConnection mangoDBConnection;

    @Autowired
    public FormController(MangoDBConnection mangoDBConnection) {
        this.mangoDBConnection = mangoDBConnection;
    }

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

        mangoDBConnection.insertData(jobOrderForm);

        // Add form data to the model to display in the result view
        model.addAttribute("jobOrderForm", jobOrderForm);

        // Create email body
        StringBuilder body = new StringBuilder();
        body.append("<!DOCTYPE html>");
        body.append("<html lang='en' style='font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", \"Helvetica Neue\", sans-serif;'>");
        body.append("<head>");
        body.append("<meta charset='UTF-8'>");
        body.append("<meta name='viewport' content='width=device-width, initial-scale=1.0'>");
        body.append("<title>Job Order Form Submission</title>");
        body.append("<style>");
        body.append("body { margin: 0; padding: 0; background-color: #f9f9f9; }");
        body.append("table { width: 100%; border-collapse: collapse; border-spacing: 0; }");
        body.append("th, td { padding: 10px; border-bottom: 1px solid #ddd; }");
        body.append("th { background-color: #f0f0f0; }");
        body.append("a { text-decoration: none; color: #337ab7; }");
        body.append("a:hover { color: #23527c; }");
        body.append("</style>");
        body.append("</head>");
        body.append("<body>");
        body.append("<header style='background-color: #333; color: #fff; padding: 20px; text-align: center;'>");
        body.append("<h2>Job Order Form Submission</h2>");
        body.append("</header>");
        body.append("<main style='max-width: 600px; margin: 40px auto; padding: 20px; background-color: #fff; border: 1px solid #ddd; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>");
        body.append("<table>");
        body.append("<tr><th>Job Order Type:</th><td>").append(jobOrderForm.getJobOrderType()).append("</td></tr>");
        body.append("<tr><th>Client Name:</th><td>").append(jobOrderForm.getClientName()).append("</td></tr>");
        body.append("<tr><th>Address:</th><td>").append(jobOrderForm.getAddress()).append("</td></tr>");
        body.append("<tr><th>Contact:</th><td>").append(jobOrderForm.getContact()).append("</td></tr>");
        body.append("<tr><th>Initial Concern:</th><td>").append(jobOrderForm.getRequestRecommendation()).append("</td></tr>");
        body.append("<tr><th>Team Leader:</th><td>").append(jobOrderForm.getLeader()).append("</td></tr>");
        body.append("<tr><th>Transportation:</th><td>").append(jobOrderForm.getServiceRequest()).append("</td></tr>");
        body.append("<tr><th>Date Issued:</th><td>").append(jobOrderForm.getDateIssued()).append("</td></tr>");
        body.append("<tr><th>Expected Due:</th><td>").append(jobOrderForm.getDateDue()).append("</td></tr>");
        body.append("<tr><th>Job Code:</th><td>").append(jobOrderForm.getJobCode()).append("</td></tr>");
        body.append("</table>");
        body.append("<p style='margin-top: 20px;'>Click <a href='").append("localhost:8080/joborder/updateStatus?jobCode=").append(jobOrderForm.getJobCode()).append("&status=confirmed'>here</a> to confirm.</p>");
        body.append("</main>");
        body.append("</body>");
        body.append("</html>");

        // Define the list of recipients
        List<String> recipients = Arrays.asList("xabubakarzx@gmail.com", "remnus013@gmail.com");

        // Send email to multiple recipients
        EmailConfig.sendEmail(recipients, "notmycandy56@gmail.com", "smtp.gmail.com", "Job Order Form Submission", body.toString(), null);


        // Return the name of the view to render the result
        return "result"; // Name of the result view
    }
}
