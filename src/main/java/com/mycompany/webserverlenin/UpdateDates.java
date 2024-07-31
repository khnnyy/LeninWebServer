package com.mycompany.webserverlenin;

import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class UpdateDates {

    private final MangoDBConnection mangoDBConnection;

    @Autowired
    public UpdateDates(MangoDBConnection mangoDBConnection) {
        this.mangoDBConnection = mangoDBConnection;
    }

    @Scheduled(cron = "0 0 0 * * ?")  // Scheduled to run at midnight every day
    public void updateRunningDays() {
        try {
            MongoCollection<Document> collection = mangoDBConnection.getCollection();
            List<Document> projectData = collection.find(new Document("date_confirmed", new Document("$ne", "-")))
                    .projection(new Document("date_confirmed", 1).append("running_days", 1))
                    .into(new ArrayList<>());

            LocalDate currentDate = LocalDate.now();

            for (Document project : projectData) {
                LocalDate confirmedDate = LocalDate.parse(project.getString("date_confirmed"), DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                int runDays = (int) ChronoUnit.DAYS.between(confirmedDate, currentDate);

                Document updateDoc = new Document("$set", new Document("running_days", runDays));
                collection.updateOne(new Document("_id", project.getObjectId("_id")), updateDoc);
            }
        } catch (MongoException e) {
            e.printStackTrace();
        }
    }
}
