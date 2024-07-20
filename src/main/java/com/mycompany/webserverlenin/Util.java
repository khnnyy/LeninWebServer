/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webserverlenin;

/**
 *
 * @author khanny
 */

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.ZoneId;

public class Util {
    
    public static String getTimeDate() {
        // Define the date and time format
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy | HH:mm:ss");
        // Get the current date and time in the Philippine time zone
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Manila"));
        // Return the formatted date and time
        return dtf.format(now);
    }
    
    public static String getDate() {
        // Define the date format
        DateTimeFormatter dtfDate = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        // Get the current date and time in the Philippine time zone
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Manila"));
        // Extract only the date part and format it
        return dtfDate.format(now);
    }
}
