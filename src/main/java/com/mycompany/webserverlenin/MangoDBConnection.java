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


    @Autowired
    public MangoDBConnection() {
        MongoClient mongoClient = MongoClients.create("mongodb+srv://khadeem:lenin_JO@clusterleninjo.divpuaq.mongodb.net/LeninJobOrder?retryWrites=true&w=majority&appName=ClusterLeninJO");
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
        

    public void updateRunningDays() {
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

        // Fetch documents where date_confirmed is not "-"
        List<Document> documentsToUpdate = collection.find(
                new Document("date_confirmed", new Document("$ne", "-"))
        ).projection(new Document("_id", 1).append("date_confirmed", 1)).into(new ArrayList<>());

        for (Document project : documentsToUpdate) {
            String confirmedDateString = project.getString("date_confirmed");

            if (confirmedDateString != null) {
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



    

    public void insertData(JOVar att) {
        try {
            Document document = new Document("_id", new ObjectId())
                    .append("job_type", att.getJobOrderType())
                    .append("job_code", att.getJobCode())
                    .append("client_name", att.getClientName())
                    .append("address", att.getAddress())
                    .append("contact", att.getContact())
                    .append("concern", att.getConcern())
                    .append("leader", att.getLeader())
                    .append("transportation", att.getTranspo())
                    .append("date_issued", att.getDateIssued())
                    .append("date_due", att.getDateDue())
                    .append("status", att.getStatus());

            collection.insertOne(document);
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }

    public String jobCode(JOVar att) {
        try {
            List<Document> jobs = collection.find(eq("job_type", att.getJobOrderType()))
                    .sort(descending("job_code"))
                    .limit(1)
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

            if (!jobs.isEmpty()) {
                Document latestJob = jobs.get(0);
                String jobCode = latestJob.getString("job_code");
                System.out.println("Latest Job Code: " + jobCode);

                String numericPart = jobCode.substring(jobCode.lastIndexOf("-") + 1);
                int jobCodeInt = Integer.parseInt(numericPart) + 1;

                String newJobCode = String.format("JO-%s-%04d", prefix, jobCodeInt);
                System.out.println("New Job Code: " + newJobCode);
                return newJobCode;
            } else {
                String newJobCode = String.format("JO-%s-0001", prefix);
                System.out.println("New Job Code: " + newJobCode);
                return newJobCode;
            }
        } catch (MongoException e) {
            e.printStackTrace();
            return null;
        }
    }
}
