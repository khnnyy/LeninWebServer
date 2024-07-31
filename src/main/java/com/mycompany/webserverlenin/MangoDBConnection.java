/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webserverlenin;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Indexes.descending;
import com.mongodb.client.model.Updates;
import jakarta.annotation.PreDestroy;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import org.bson.conversions.Bson;

@Component
public class MangoDBConnection {
    
    @Autowired
    private final MongoDatabase database;
    private final MongoCollection<Document> collection;
    private final MongoCollection<Document> configuration;
    private final MongoClient mongoClient;


    @Autowired
    public MangoDBConnection() {
             mongoClient = MongoClients.create("mongodb+srv://khadeem:lenin_JO@clusterleninjo.divpuaq.mongodb.net/LeninJobOrder?retryWrites=true&w=majority&appName=ClusterLeninJO");
        this.database = mongoClient.getDatabase("LeninJobOrder");
        this.collection = database.getCollection("solutionsClient");
        this.configuration = database.getCollection("solutionsConfig");
    }
    
    public MongoCollection<Document> getCollection() {
        return collection;
    }
    
    public MongoCollection<Document> getConfiguration() {
        return configuration;
    }
    
    
    
    public List<Document> getProjectData() {
        List<Document> projectData = new ArrayList<>();
        try {
          projectData = collection.find()
              .projection(new Document("job_code", 1)
                      .append("client_name", 1)
                      .append("status", 1)
                      .append("date_issued", 1)
                      .append("date_confirmed", 1)
                      .append("date_due", 1))
              .sort(new Document("date_issued", -1))  // Sort by "date_issued" in descending order
              .into(new ArrayList<>());
        } catch (MongoException e) {
          e.printStackTrace();
        }
        return projectData;
    }
    

    public void updateStatusByJobCode(String jobCode, String newStatus) {
        try {
            Document query = new Document("job_code", jobCode);
            Document update = new Document("$set", new Document("status", newStatus));
            collection.updateOne(query, update);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
    
    public void confirmStatusByJobCode(String jobCode, String newConfirmed){
        try{
            Document query = new Document("job_code", jobCode);
            Document confirm = new Document("$set", new Document("date_confirmed", newConfirmed));
            collection.updateOne(query, confirm);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
        

//    public void updateRunningDays() {
//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
//
//        // Fetch documents where date_confirmed is not "-"
//        List<Document> documentsToUpdate = collection.find(
//                new Document("date_confirmed", new Document("$ne", "-"))
//        ).projection(new Document("_id", 1).append("date_confirmed", 1)).into(new ArrayList<>());
//
//        for (Document project : documentsToUpdate) {
//            String confirmedDateString = project.getString("date_confirmed");
//
//            if (confirmedDateString != null) {
//                try {
//                    LocalDate confirmedDate = LocalDate.parse(confirmedDateString, formatter);
//                    int runningDays = (int) ChronoUnit.DAYS.between(confirmedDate, currentDate);
//
//                    // Update the running_days field as a string for the current document
//                    String runningDaysStr = String.valueOf(runningDays);
//                    Bson updateDoc = Updates.set("running_days", runningDaysStr);
//                    collection.updateOne(new Document("_id", project.getObjectId("_id")), updateDoc);
//                } catch (Exception e) {
//                    // Handle the exception (e.g., log the error)
//                    e.printStackTrace();
//                }
//            }
//        }
//    }
    
    public void updateRunningDays() {
    LocalDate currentDate = LocalDate.now();
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    // Fetch documents where date_confirmed is not "-"
    List<Document> documentsToUpdate = collection.find(
            new Document("date_confirmed", new Document("$ne", "-"))
    ).projection(new Document("_id", 1).append("date_confirmed", 1).append("status", 1)).into(new ArrayList<>());

    for (Document project : documentsToUpdate) {
        String confirmedDateString = project.getString("date_confirmed");
        String status = project.getString("status");

        if (confirmedDateString != null && !status.equals("deployed")) {
            try {
                LocalDate confirmedDate = LocalDate.parse(confirmedDateString, formatter);
                int runningDays = (int) ChronoUnit.DAYS.between(confirmedDate, currentDate);

                // Update the running_days field as a string for the current document
                String runningDaysStr = String.valueOf(runningDays);
                Bson updateDoc = Updates.set("running_days", runningDaysStr);
                collection.updateOne(new Document("_id", project.getObjectId("_id")), updateDoc);
            } catch (Exception e) {
                // Handle the exception (e.g., log the error)
                e.printStackTrace();
            }
        }
    }
}

    
    
    public void updateWarranty() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Fetch documents where status is "completed"
        List<Document> documentsToUpdate = collection.find(
                new Document("status", "completed")
        ).projection(new Document("_id", 1).append("status", 1).append("warranty_date", 1).append("warranty", 1)).into(new ArrayList<>());

        for (Document project : documentsToUpdate) {
            String warrantyDateString = project.getString("warranty_date");

            if (warrantyDateString != null) {
                try {
                    LocalDate warrantyDate = LocalDate.parse(warrantyDateString, formatter);
                    // Calculate 90 days from the warranty date
                    LocalDate ninetyDaysLater = warrantyDate.plusDays(90);

                    // Calculate running days countdown
                    long runningDays = ChronoUnit.DAYS.between(currentDate, ninetyDaysLater);

                    // Update the running_days field as a string for the current document
                    String runningDaysStr = String.valueOf(runningDays);
                    Bson updateDoc = Updates.set("warranty", runningDaysStr);
                    collection.updateOne(new Document("_id", project.getObjectId("_id")), updateDoc);
                } catch (Exception e) {
                    // Handle the exception (e.g., log the error)
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertData(JobOrderForm jobOrderForm) {
        try {
            Document document = new Document("_id", new ObjectId())
                    .append("job_type", jobOrderForm.getJobOrderType())
                    .append("job_code", jobOrderForm.getJobCode())
                    .append("client_name", jobOrderForm.getClientName())
                    .append("client_contact", jobOrderForm.getContact())
                    .append("client_address", jobOrderForm.getAddress())
                    .append("client_request", jobOrderForm.getRequestRecommendation())
                    .append("team_leader", jobOrderForm.getLeader())
                    .append("solution_manpower", jobOrderForm.getManPower())
                    .append("date_issued", jobOrderForm.getDateIssued())
                    .append("time_issued", jobOrderForm.getTimeIssued())
                    .append("date_due", jobOrderForm.getDateDue())
                    .append("solution_instructions", jobOrderForm.getInstructions())
                    .append("date_confirmed", "-")
                    .append("running_days", "-")
                    .append("date_deployed", "-")
                    .append("time_deployed", "-")
                    .append("date_released","-")
                    .append("time_released", "-")
                    .append("warranty", "-")
                    .append("status", "pending"); // Assuming status is set to "Pending" initially

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
            collection.insertOne(document);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

   public String jobCode(JOVar att) {
    try {
        List<Document> jobs = collection.find(eq("job_type", att.getJobOrderType()))
                                        .sort(descending("job_code"))
                                        .into(new ArrayList<>());

        String prefix;
        switch (att.getJobOrderType()) {
            case "OCCULAR":
                prefix = "OC";
                break;
            case "CM":
                prefix = "CM";
                break;
            case "PROJECT":
                prefix = "PR";
                break;
            default:
                prefix = "XX";
                break;
        }

        int maxNumericPart = 0;
        for (Document job : jobs) {
            String jobCode = job.getString("job_code");
            String numericPart = jobCode.substring(jobCode.lastIndexOf("-") + 1);
            try {
                int num = Integer.parseInt(numericPart);
                maxNumericPart = Math.max(maxNumericPart, num);
            } catch (NumberFormatException e) {
                // Handle invalid numeric part, e.g., log or throw an exception
                System.err.println("Invalid job code format: " + jobCode);
            }
        }

        int newJobCodeInt = maxNumericPart + 1;
        String newJobCode = String.format("JO-%s-%04d", prefix, newJobCodeInt);
        System.out.println("New Job Code: " + newJobCode);
        return newJobCode;
    } catch (MongoException e) {
        e.printStackTrace();
        // Handle exception, e.g., return a default value or log the error
        return "Error generating job code";
    }
}


}
