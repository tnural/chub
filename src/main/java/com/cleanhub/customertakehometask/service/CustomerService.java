package com.cleanhub.customertakehometask.service;

import com.cleanhub.customertakehometask.dao.CustomerDao;
import com.cleanhub.customertakehometask.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;
@Service
public class CustomerService {
    @Autowired
    CustomerDao customerDao;
    public Optional<List<Customer>> findAll(){
        return Optional.of(this.customerDao.findAll());
    }
    public Optional<Customer> findCustomerById(String id){
        if(!this.customerDao.existsById(id))
            return Optional.empty();
        return this.customerDao.findById(id);
    }
    public Optional<LinkedHashMap<String, Double>> getTopCustomerInterval(Integer topXCustomer, Date from, Date to){
        var result = this.customerDao.getCustomersByDurationAndLimit(topXCustomer, from, to);
        if(result.isEmpty())
            return Optional.empty();
        var resultMap = new LinkedHashMap<String, Double>();
        result.get().forEach(element -> {
            resultMap.put(customerDao.findById(element.getId()).get().getCompanyName(), element.getQuantity());
        });
        return Optional.of(resultMap);
    }
}
