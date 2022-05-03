package com.cleanhub.customertakehometask.controller;

import com.cleanhub.customertakehometask.dto.TopCustomerIntervalDto;
import com.cleanhub.customertakehometask.entity.Customer;
import com.cleanhub.customertakehometask.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping()
    public ResponseEntity<List<Customer>> getAllCustomer(){
        return new ResponseEntity<>(this.customerService.findAll().get(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Customer> getAllCustomer(String id){
        var resp = this.customerService.findCustomerById(id);
        if(resp.isEmpty())
            return ResponseEntity.unprocessableEntity().build();
        return new ResponseEntity<Customer>((Customer) resp.get(), HttpStatus.OK);
    }

    @GetMapping("/top")
    public ResponseEntity<LinkedHashMap<String, Double>> getTopNumberOfCustomerInterval(@RequestBody TopCustomerIntervalDto dto) {
        var customerList = customerService.getTopCustomerInterval(dto.getLimit(), dto.getFrom(), dto.getTo());
        if (customerList.isEmpty())
            return ResponseEntity.unprocessableEntity().build();

        return new ResponseEntity<>(customerList.get(), HttpStatus.OK);
    }
}
