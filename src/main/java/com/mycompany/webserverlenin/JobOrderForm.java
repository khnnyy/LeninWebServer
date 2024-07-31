package com.mycompany.webserverlenin;

public class JobOrderForm {
    private String dateIssued;
    private String jobCode;
    private String jobCodeText;
    private String clientName;
    private String contact;
    private String requestRecommendation;
    private String address;
    private String dateDeployed;
    private String serviceRequest;
    private String leader;
    private String dateDue;
    private int manPower;
    private String instructions;
    private String jobOrderType;
    private String timeIssued;

    // Getters and setters for all fields, including jobOrderType
    public String getJobOrderType() {
        return jobOrderType;
    }

    public void setJobOrderType(String jobOrderType) {
        this.jobOrderType = jobOrderType;
    }

    // Getters and Setters

    public String getDateIssued() {
        return dateIssued;
    }

    public void setDateIssued(String dateIssued) {
        this.dateIssued = dateIssued;
    }
    
    public String getTimeIssued(){
        return timeIssued;
    }
    
    public void setTimeIssued(String timeIssued){
        this.timeIssued = timeIssued;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getJobCodeText() {
        return jobCodeText;
    }

    public void setJobCodeText(String jobCodeText) {
        this.jobCodeText = jobCodeText;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getRequestRecommendation() {
        return requestRecommendation;
    }

    public void setRequestRecommendation(String requestRecommendation) {
        this.requestRecommendation = requestRecommendation;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDateDeployed() {
        return dateDeployed;
    }

    public void setDateDeployed(String dateDeployed) {
        this.dateDeployed = dateDeployed;
    }

    public String getServiceRequest() {
        return serviceRequest;
    }

    public void setServiceRequest(String serviceRequest) {
        this.serviceRequest = serviceRequest;
    }

    public String getLeader() {
        return leader;
    }

    public void setLeader(String leader) {
        this.leader = leader;
    }

    public String getDateDue() {
        return dateDue;
    }

    public void setDateDue(String dateDue) {
        this.dateDue = dateDue;
    }

    public int getManPower() {
        return manPower;
    }

    public void setManPower(int manPower) {
        this.manPower = manPower;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
