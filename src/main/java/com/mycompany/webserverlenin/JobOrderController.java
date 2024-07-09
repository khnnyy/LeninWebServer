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


        @GetMapping("/updateStatus")
        public String updateStatusByJobCode(@RequestParam String jobCode) {
        String status = "confirmed";
        try {
            System.out.println("Received request to update job code: " + jobCode);
            mangoDBConnection.updateStatusByJobCode(jobCode, status);
            System.out.println("Status updated to confirmed for job code: " + jobCode);
            return "<html>" +
                    "<body>" +
                    "<h1>Status Update</h1>" +
                    "<p>Job Code: <strong>" + jobCode + "</strong> has been confirmed at " + Util.getTimeDate() + ".</p>" +
                    "</body>" +
                    "</html>";
        } catch (Exception e) {
            e.printStackTrace();
            return "<html>" +
                    "<body>" +
                    "<h1>Error</h1>" +
                    "<p>Error updating status: " + e.getMessage() + "</p>" +
                    "</body>" +
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
