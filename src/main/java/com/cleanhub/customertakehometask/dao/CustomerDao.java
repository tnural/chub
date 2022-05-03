package com.cleanhub.customertakehometask.dao;

import com.cleanhub.customertakehometask.entity.Customer;
import com.cleanhub.customertakehometask.mapper.CustomerQuantity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.*;
public interface CustomerDao extends JpaRepository<Customer, String> {

    @Query(value="select co.customer_id as id, sum(quantity) as quantity from contract co \n" +
            "left join customer cu\n" +
            "on cu.id = co.customer_id\n" +
            "where \n" +
            "co.is_fullfilled = true\n" +
            "and start_date_time>=:from \n" +
            "and end_date_time<=:to\n" +
            "group by co.customer_id \n" +
            "order by sum(co.quantity) \n" +
            "desc limit :limit", nativeQuery=true)
    Optional<List<CustomerQuantity>> getCustomersByDurationAndLimit(@Param("limit") Integer limit, @Param("from") Date from, @Param("to") Date to);

}

