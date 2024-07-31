/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webserverlenin;

/**
 *
 * @author khanny
 */

    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.web.bind.annotation.*;

    @RestController
    @RequestMapping("/joborder")
    public class JobOrderController {

        private final MangoDBConnection mangoDBConnection;

        @Autowired
        public JobOrderController(MangoDBConnection mangoDBConnection) {
            this.mangoDBConnection = mangoDBConnection;
        }

        @PostMapping("/insert")
        public String insertData(@RequestBody JOVar jobOrder) {
            mangoDBConnection.insertData(jobOrder);
            return "Job Order inserted successfully";
        }
        

        @GetMapping("/jobcode")
        public String getJobCode(@RequestParam String jobOrderType) {
            JOVar jobOrder = new JOVar();
            jobOrder.setJobOrderType(jobOrderType);
            return mangoDBConnection.jobCode(jobOrder);
        }
        
        @GetMapping("/health")
        public String healthCheck() {
            
        return "Application is up and running!";
        }
        

        @GetMapping("/status")
        public String getStatusByJobCode(@RequestParam String jobCode) {
            // Implement a method to fetch status by job code if required
            return "Status fetching not implemented";
        }

//        @GetMapping("/updateStatus")
//        public String updateStatusByJobCode(@RequestParam String jobCode, @RequestParam String newStatus) {
//          mangoDBConnection.updateStatusByJobCode(jobCode, newStatus);
//          System.out.println("newStatus");
//          return "Status updated successfully";
//        }
//    }


//        @GetMapping("/updateStatus")
//        public String updateStatusByJobCode(@RequestParam String jobCode) {
//        String status = "confirmed";
//        try {
//            System.out.println("Received request to update job code: " + jobCode);
//            mangoDBConnection.updateStatusByJobCode(jobCode, status);
//            System.out.println("Status updated to confirmed for job code: " + jobCode);
//            return "<html>" +
//                    "<body>" +
//                    "<h1>Job Order Status Update</h1>" +
//                    "<p>Job Code: <strong>" + jobCode + "</strong> has been confirmed at <strong>" + Util.getTimeDate() + "</strong>.</p>" +
//                    "<p>You may now close this tab</p>" +
//                    "</body>" +
//                    "</html>";
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "<html>" +
//                    "<body>" +
//                    "<h1>Error</h1>" +
//                    "<p>Error updating status: " + e.getMessage() + "</p>" +
//                    "please contact developer" +
//                    "</body>" +
//                    "</html>";
//        }
//    }
//    }
        
        @GetMapping("/updateStatus")
public String updateStatusByJobCode(@RequestParam String jobCode) {
    String status = "in progress";
    String confirmed = Util.getDate();
    try {
        System.out.println("Received request to update job code: " + jobCode);
        mangoDBConnection.updateStatusByJobCode(jobCode, status);
        mangoDBConnection.confirmStatusByJobCode(jobCode, confirmed);
        System.out.println("Status updated to confirmed for job code: " + jobCode);
        return "<!DOCTYPE html>" +
                "<html lang='en' style='font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", \"Helvetica Neue\", sans-serif;'>"+
                "<head>"+
                "<meta charset='UTF-8'>"+
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"+
                "<title>Job Order Status Update</title>"+
                "<style>"+
                "body { margin: 0; padding: 0; background-color: #f9f9f9; }"+
                "h1 { color: #333; margin-bottom: 10px; }"+
                "p { margin-bottom: 20px; }"+
                "strong { font-weight: bold; }"+
                "</style>"+
                "</head>"+
                "<body>"+
                "<header style='background-color: #333; color: #fff; padding: 20px; text-align: center;'>"+
                "<h1>Job Order Status Update</h1>"+
                "</header>"+
                "<main style='max-width: 600px; margin: 40px auto; padding: 20px; background-color: #fff; border: 1px solid #ddd; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>"+
                "<p>Job Order: <strong>" + jobCode + "</strong> has been confirmed at <strong>" + Util.getDate() + "</strong>.</p>"+ 
                "<p>Job Order: <strong>" + jobCode + "</strong> will now be flagged as <strong>in progress</strong>.</p>"+                
                "<p>You may now close this tab</p>"+
                "</main>"+
                "</body>"+
                "</html>";
    } catch (Exception e) {
        e.printStackTrace();
        return "<!DOCTYPE html>" +
                "<html lang='en' style='font-family: -apple-system, BlinkMacSystemFont, \"Segoe UI\", Roboto, Oxygen, Ubuntu, Cantarell, \"Open Sans\", \"Helvetica Neue\", sans-serif;'>"+
                "<head>"+
                "<meta charset='UTF-8'>"+
                "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"+
                "<title>Error</title>"+
                "<style>"+
                "body { margin: 0; padding: 0; background-color: #f9f9f9; }"+
                "h1 { color: #333; margin-bottom: 10px; }"+
                "p { margin-bottom: 20px; color: #red; }"+
                "strong { font-weight: bold; }"+
                "</style>"+
                "</head>"+
                "<body>"+
                "<header style='background-color: #333; color: #fff; padding: 20px; text-align: center;'>"+
                "<h1>Error</h1>"+
                "</header>"+
                "<main style='max-width: 600px; margin: 40px auto; padding: 20px; background-color: #fff; border: 1px solid #ddd; border-radius: 10px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);'>"+
                "<p>Error updating status: <strong>" + e.getMessage() + "</strong></p>"+
                "<p>Please contact the developer</p>"+
                "</main>"+
                "</body>"+
                "</html>";
    }
}
    }
    //    @GetMapping("/updateStatus")
    //    public String updateStatus(@RequestParam String jobCode, @RequestParam String status) {
    //        mangoDBConnection.updateStatusByJobCode(jobCode, status);
    //        
    //        return "Status updated successfully for job code: " + jobCode;
    //    }
    //}
