package com.cleanhub.customertakehometask.controller;

import com.cleanhub.customertakehometask.entity.Contract;
import com.cleanhub.customertakehometask.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/contract")
public class ContractController {
    @Autowired
    ContractService contractService;

    @GetMapping()
    public ResponseEntity<List<Contract>> getAllContracts(){
        return new ResponseEntity<>(this.contractService.getAllContracts(), HttpStatus.OK);
    }
}
