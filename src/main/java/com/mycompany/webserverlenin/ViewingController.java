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

import com.itextpdf.io.exceptions.IOException;
import com.itextpdf.io.source.ByteArrayOutputStream;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.element.Paragraph;
import java.io.ByteArrayInputStream;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;

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
    
//    @GetMapping("/download-pdf/{jobCode}")
//    public ResponseEntity<byte[]> downloadPdf(@PathVariable String jobCode) {
//        try {
//            // Fetch project details
//            List<Document> projectDetails = viewingService.getProjectDetail(jobCode);
//            if (projectDetails == null || projectDetails.isEmpty()) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//
//            // Generate PDF
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PdfWriter writer = new PdfWriter(baos);
//            PdfDocument pdf = new PdfDocument(writer);
//            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);
//
//            // Add project details to PDF
//            Document project = projectDetails.get(0);
//            document.add(new Paragraph("Job Type: " + project.getString("job_type")));
//            document.add(new Paragraph("Job Code: " + project.getString("job_code")));
//            document.add(new Paragraph("Client Name: " + project.getString("client_name")));
//            document.add(new Paragraph("Address: " + project.getString("address")));
//            document.add(new Paragraph("Contact: " + project.getString("contact")));
//            document.add(new Paragraph("Concern: " + project.getString("concern")));
//            document.add(new Paragraph("Leader: " + project.getString("leader")));
//            document.add(new Paragraph("Date Due: " + project.getString("date_due")));
//            document.add(new Paragraph("Date Issued: " + project.getString("date_issued")));
//            document.add(new Paragraph("Date Confirmed: " + project.getString("date_confirmed")));
//            document.add(new Paragraph("Running Days: " + project.getString("running_days")));
//            document.add(new Paragraph("Warranty: " + project.getString("warranty")));
//            document.add(new Paragraph("Status: " + project.getString("status")));
//
//            document.close();
//
//            // Return PDF as response
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
//            headers.setContentDispositionFormData("attachment", "job_details_" + jobCode + ".pdf");
//            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
//    
//     @GetMapping("/downloadPdf/{jobCode}")
//    public ResponseEntity<InputStreamResource> downloadPdf(@PathVariable String jobCode) throws IOException {
//        List<Document> projectDetails = viewingService.getProjectDetail(jobCode);
//        if (projectDetails != null && !projectDetails.isEmpty()) {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream();
//            PdfWriter writer = new PdfWriter(baos);
//            PdfDocument pdf = new PdfDocument(writer);
//            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);
//
//            Document projectDetail = projectDetails.get(0);
//            document.add(new Paragraph("Job Code: " + projectDetail.getString("job_code")));
//            document.add(new Paragraph("Client Name: " + projectDetail.getString("client_name")));
//            document.add(new Paragraph("Status: " + projectDetail.getString("status")));
//            document.add(new Paragraph("Date Issued: " + projectDetail.getString("date_issued")));
//            document.add(new Paragraph("Date Confirmed: " + projectDetail.getString("date_confirmed")));
//            document.add(new Paragraph("Running Days: " + projectDetail.getString("running_days")));
//            document.add(new Paragraph("Warranty: " + projectDetail.getString("warranty")));
//
//            document.close();
//
//            ByteArrayInputStream bis = new ByteArrayInputStream(baos.toByteArray());
//            HttpHeaders headers = new HttpHeaders();
//            headers.add("Content-Disposition", "inline; filename=job_code_" + jobCode + ".pdf");
//
//            return ResponseEntity.ok()
//                    .headers(headers)
//                    .contentType(MediaType.APPLICATION_PDF)
//                    .body(new InputStreamResource(bis));
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
    @GetMapping("/download-pdf/{jobCode}")
    public ResponseEntity<byte[]> downloadPdf(@PathVariable String jobCode) {
        try {
            // Fetch project details
            List<Document> projectDetails = viewingService.getProjectDetail(jobCode);
            if (projectDetails == null || projectDetails.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Generate PDF
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            com.itextpdf.layout.Document document = new com.itextpdf.layout.Document(pdf);

            // Add project details to PDF
            Document project = projectDetails.get(0);
            document.add(new Paragraph("Job Type: " + project.getString("job_type")));
            document.add(new Paragraph("Job Code: " + project.getString("job_code")));
            document.add(new Paragraph("Client Name: " + project.getString("client_name")));
            document.add(new Paragraph("Address: " + project.getString("address")));
            document.add(new Paragraph("Contact: " + project.getString("contact")));
            document.add(new Paragraph("Concern: " + project.getString("concern")));
            document.add(new Paragraph("Leader: " + project.getString("leader")));
            document.add(new Paragraph("Date Due: " + project.getString("date_due")));
            document.add(new Paragraph("Date Issued: " + project.getString("date_issued")));
            document.add(new Paragraph("Date Confirmed: " + project.getString("date_confirmed")));
            document.add(new Paragraph("Running Days: " + project.getString("running_days")));
            document.add(new Paragraph("Warranty: " + project.getString("warranty")));
            document.add(new Paragraph("Status: " + project.getString("status")));

            document.close();

            // Return PDF as response
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(org.springframework.http.MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "job_details_" + jobCode + ".pdf");
            return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

    