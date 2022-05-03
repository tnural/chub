package com.cleanhub.customertakehometask.service;

import com.cleanhub.customertakehometask.dao.ContractDao;
import com.cleanhub.customertakehometask.entity.Contract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class ContractService {
    @Autowired
    ContractDao contractDao;
    public List<Contract> getAllContracts(){
        return this.contractDao.findAll();
    }
}
