package com.mycompany.webserverlenin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.thymeleaf.context.Context;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import org.thymeleaf.spring6.SpringTemplateEngine;

@Controller
public class FormController {

    private final MangoDBConnection mangoDBConnection;
    private final SpringTemplateEngine templateEngine;

    @Autowired
    public FormController(MangoDBConnection mangoDBConnection, SpringTemplateEngine templateEngine) {
        this.mangoDBConnection = mangoDBConnection;
        this.templateEngine = templateEngine;
    }

    @GetMapping("/job-order-form")
    public String showJobOrderForm(Model model) {
        model.addAttribute("jobOrderForm", new JobOrderForm());
        return "jobOrderForm";
    }

    @PostMapping("/submit-job-order")
    public String submitJobOrder(@ModelAttribute JobOrderForm jobOrderForm, Model model) {
        mangoDBConnection.insertData(jobOrderForm);
        model.addAttribute("jobOrderForm", jobOrderForm);

        String emailBody = createEmailBody(jobOrderForm);

        List<String> recipients = Arrays.asList("xabubakarzx@gmail.com", "remnus013@gmail.com");

        try {
            EmailConfig.sendEmail(recipients, "notmycandy56@gmail.com", "smtp.gmail.com", "Job Order Form Submission", emailBody, null);
        } catch (Exception e) {
            e.printStackTrace();
            // Handle the exception appropriately (e.g., add an error message to the model)
        }

        return "result";
    }

    private String createEmailBody(JobOrderForm jobOrderForm) {
        Context context = new Context();
        context.setVariable("jobOrderForm", jobOrderForm);
        context.setVariable("recipientName", "Recipient"); // Set recipient name if available
        return templateEngine.process("emailTemplate", context);
    }
}
