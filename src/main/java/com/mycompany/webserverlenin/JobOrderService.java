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
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class JobOrderService {

  @Autowired
  private JobOrderRepository joVarRepository;

  public void updateJobOrderStatus(String jobCode, String newStatus) {
    joVarRepository.updateStatusByJobCode(jobCode, newStatus);
  }
}

