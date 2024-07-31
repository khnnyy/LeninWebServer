package com.mycompany.webserverlenin;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
public class KeepAliveTask {

    private static final long INTERVAL = 5 * 60 * 1000; // 10 minutes in milliseconds

    private static final String TARGET_URL = "https://leninwebserver.onrender.com/health"; // Replace with your URL

    @Scheduled(fixedRate = INTERVAL)
    public void logKeepAlive() {
        System.out.println("Keeping myself alive at " + Util.getTimeDate());
        pingURL(TARGET_URL);
    }

    private void pingURL(String urlString) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL(urlString).openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(30000);
            connection.setReadTimeout(30000);

            int responseCode = connection.getResponseCode();
            System.out.println(responseCode == 200 
                    ? "Ping successful to " + urlString 
                    : "Ping failed to " + urlString + " with response code: " + responseCode);
        } catch (IOException e) {
            System.out.println("Exception while pinging URL: " + e.getMessage());
        }
    }
}
