/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.webserverlenin;

/**
 *
 * @author khanny
 */
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

/**
 *
 * @author khanny
 */

@Document(collection = "JOVar")
@Component
public class JOVar {
    
    @Id
    private String jobOrderType;
    private String jobCode;
    private String clientName;
    private String address;
    private String contact;
    private String concern;
    private String leader;
    private String transpo;
    private String dateIssued;
    private String dateConfirmed;
    private String dateDue;
    private String status;
    private String confirmationToken;
    
    private String user;
    private String password;
    private String role;
    
    private String cluster;
    private String database;
    private String collection;
    
//    private SecretKey key;
//    private IvParameterSpec iv;
    
    @Transient
    private long runningDays;
    
    public long getRunningDays() {
        return runningDays;
    }

    public void setRunningDays(long runningDays) {
        this.runningDays = runningDays;
    }
    public JOVar() {
        this.confirmationToken = ""; // Initialize token as empty string
    }
    
    public String getConfirmationToken() {
        return confirmationToken;
    }
    
//    public void setKey(SecretKey key){
//        this.key = key;
//    }
//    
//    public SecretKey getKey(){
//        return this.key;
//    }
//    
//    public void setIv(IvParameterSpec iv){
//        this.iv = iv;
//    }
//    
//    public IvParameterSpec getIv(){
//        return this.iv;
//    }
        // Getters and setters
    
    public void setUser(String user){
        this.user = user;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public void setRole(String role){
        this.role = role;
    }
//    
//    public void setCluster(String cluster){
//        this.cluster = cluster;
//    }
//    
//    public void setDatabase(String database){
//        this.database = database;
//    }
//    
//    public void setCollection(String collection){
//        this.collection = collection;
//    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }
            
    
    public void setJobOrderType(String jobOrderType){
        this.jobOrderType = jobOrderType;
        
    }
    
    public void setJobCode(String jobCode){
        this.jobCode = jobCode;
        
    }
    
    public void setClientName(String clientName){
        this.clientName = clientName;
        
    }
    
    public void setAddress(String address){
        this.address = address;
        
    }
    
    public void setContact(String contact){
        this.contact = contact;
        
    }
    
    public void setConcern(String concern){
        this.concern = concern;
        
    }
    
    public void setLeader(String leader){
        this.leader = leader;
        
    }
    
    public void setTranspo(String transpo){
        this.transpo = transpo;
        
    }
    
    public void setDateIssued(String dateIssued){
        this.dateIssued = dateIssued;
        
    }
    
    public void setDateConfirmed(String dateConfirmed){
        this.dateConfirmed = dateConfirmed;
    }
    
    public void setDateDue(String dateDue){
        this.dateDue = dateDue;
        
    }
    
    public void setStatus(String status){
        this.status = status;
        
    }
    
    
    /*-----------Getter-------------*/
    
    public String getUser(){
        return this.user;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public String getRole(){
        return this.role;
    }
//    
//    public String getCluster(){
//        return this.cluster;
//    }
//    
//    public String getDatabase(){
//        return this.database;
//    }
//    
//    public String getCollection(){
//        return this.collection;
//    }
    
    public String getJobOrderType(){
        return this.jobOrderType;
        
    }
    
    
    public String getJobCode(){
        return this.jobCode;
        
    }
    
    public String getClientName(){
        return this.clientName;
        
    }
    
    public String getAddress(){
        return this.address;
        
    }
    
    public String getContact(){
        return this.contact;
        
    }
    
    public String getConcern(){
        return this.concern;
        
    }
    
    public String getLeader(){
        return this.leader;
        
    }
    
    public String getTranspo(){
        return this.transpo;
        
    }
    
    public String getDateIssued(){
        return this.dateIssued;
        
    }
    
    public String getDateConfirmed(){
        return this.dateConfirmed;
    }

    public String getDateDue(){
        return this.dateDue;
        
    }
    
    public String getStatus(){
        return this.status;
        
    }
    
           
            
            
    
}

