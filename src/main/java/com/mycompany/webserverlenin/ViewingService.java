package com.mycompany.webserverlenin;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;

@Service
public class ViewingService {

    private final MangoDBConnection mangoDBConnection;

    @Autowired
    public ViewingService(MangoDBConnection mangoDBConnection) {
        this.mangoDBConnection = mangoDBConnection;
    }

    public List<Document> getActiveProjects() {
    List<Document> projectData = new ArrayList<>();
    try {
        MongoCollection<Document> collection = mangoDBConnection.getCollection();
        mangoDBConnection.updateRunningDays();
        mangoDBConnection.updateWarranty();

        // Filter to exclude documents with status "completed"
        Document filter = new Document("status", new Document("$ne", "completed"));

        projectData = collection.find(filter)
                .projection(new Document("job_code", 1)
                        .append("client_name", 1)
                        .append("status", 1)
                        .append("date_issued", 1)
                        .append("date_confirmed", 1)
                        .append("running_days", 1)
                        .append("warranty", 1))
                .sort(new Document("date_issued", -1))
                .into(new ArrayList<>());
    } catch (MongoException e) {
        e.printStackTrace();
    }
    return projectData;
}


    public List<Document> getCompletedProjects() {
        List<Document> projectData = new ArrayList<>();
        try {
            MongoCollection<Document> collection = mangoDBConnection.getCollection();
            mangoDBConnection.updateRunningDays();
            mangoDBConnection.updateWarranty();

            // Filter to include only documents with status "completed"
            Document filter = new Document("status", "completed");

            projectData = collection.find(filter)
                    .projection(new Document("job_code", 1)
                            .append("client_name", 1)
                            .append("status", 1)
                            .append("date_issued", 1)
                            .append("date_confirmed", 1)
                            .append("running_days", 1)
                            .append("warranty", 1))
                    .sort(new Document("date_issued", -1))
                    .into(new ArrayList<>());
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return projectData;
    }

    public List<Document> getAllProjects() {
        List<Document> projectData = new ArrayList<>();
        try {
            MongoCollection<Document> collection = mangoDBConnection.getCollection();
            mangoDBConnection.updateRunningDays();
            mangoDBConnection.updateWarranty();

            projectData = collection.find()
                    .projection(new Document("job_code", 1)
                            .append("client_name", 1)
                            .append("status", 1)
                            .append("date_issued", 1)
                            .append("date_confirmed", 1)
                            .append("running_days", 1)
                            .append("warranty", 1))
                    .sort(new Document("date_issued", -1))
                    .into(new ArrayList<>());
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return projectData;
    }
    
    public List<Document> getConfirmedProjects(){
        List<Document> projectData = new ArrayList<>();
        try{MongoCollection<Document> collection = mangoDBConnection.getCollection();

            Document filter = new Document("status", "in progress");
        
            projectData = collection.find(filter)
                    .projection(new Document("job_code", 1)
                            .append("client_name", 1)
                            .append("status", 1)
                            .append("date_due", 1))
                    .sort(new Document("date_issued", -1))
                    .into(new ArrayList<>());
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return projectData;
    }
    
    public List<Document> getDeployedProjects(){
        List<Document> projectData = new ArrayList<>();
        try{MongoCollection<Document> collection = mangoDBConnection.getCollection();

            Document filter = new Document("status", "deployed");
        
            projectData = collection.find(filter)
                    .projection(new Document("job_code", 1)
                            .append("client_name", 1)
                            .append("status", 1)
                            .append("date_due", 1))
                    .sort(new Document("date_issued", -1))
                    .into(new ArrayList<>());
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return projectData;
    }
    
    public List<Document> getProjectDetail(String jobCode) {
        List<Document> projectDetail = new ArrayList<>();
        try {
            MongoCollection<Document> collection = mangoDBConnection.getCollection();
            Document filter = new Document("job_code", jobCode);
            projectDetail = collection.find(filter).into(new ArrayList<>());
        } catch (MongoException e) {
            e.printStackTrace();
        }
        return projectDetail;
    }
 
}
