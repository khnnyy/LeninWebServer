package com.mycompany.webserverlenin;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.Updates;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


@Controller
@RequestMapping("/")
public class ViewingController {

    private final MangoDBConnection mangoDBConnection;
    private final ViewingService viewingService;

    @Autowired
    public ViewingController(MangoDBConnection mangoDBConnection, ViewingService viewingService) {
        this.mangoDBConnection = mangoDBConnection;
        this.viewingService = viewingService;
    }
    
        @GetMapping("/view-details/{jobCode}")
    public String getJobOrderForm(@PathVariable String jobCode, Model model) {
        try {
            List<Document> projectDetails = viewingService.getProjectDetail(jobCode);
            if (projectDetails != null && !projectDetails.isEmpty()) {
                model.addAttribute("jobOrder", projectDetails.get(0)); // Assuming you want to display the first document
                return "viewJob"; // Name of your Thymeleaf template
            } else {
                model.addAttribute("error", "No project found with the specified job code.");
                return "error"; // Name of your error template
            }
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", "An error occurred while fetching the project details.");
            return "error"; // Name of your error template
        }
    }



    
    @GetMapping("/view")
    public String viewProjects(Model model) {
        List<Document> projects = viewingService.getAllProjects();
        model.addAttribute("projects", projects);
        return "view";
    }
    
    @GetMapping("/deployment")
    public String deployment(Model model) {
        List<Document> projects = viewingService.getConfirmedProjects();
        model.addAttribute("jobs", projects);
        return "deployment";
    }
    
    @GetMapping("/releasing")
    public String releasing(Model model) {
        List<Document> projects = viewingService.getDeployedProjects();
        model.addAttribute("jobs", projects);
        return "releasing";
    }
    

    @GetMapping("/activeFilter")
    @ResponseBody
    public List<Document> activeFilter(@RequestParam(required = false) String filter) {
        List<Document> projects;
        switch (filter) {
            case "active":
                projects = viewingService.getActiveProjects();
                break;
            case "completed":
                projects = viewingService.getCompletedProjects();
                break;
            default:
                projects = viewingService.getAllProjects();
                break;
        }
        return projects;
    }
}

    