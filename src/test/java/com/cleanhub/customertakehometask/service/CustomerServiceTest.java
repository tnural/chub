package com.cleanhub.customertakehometask.service;

import com.cleanhub.customertakehometask.dao.CustomerDao;
import com.cleanhub.customertakehometask.entity.Customer;
import com.cleanhub.customertakehometask.mapper.CustomerQuantity;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@TestPropertySource(properties = "app.scheduling.enable=false")
class CustomerServiceTest {
    @Mock
    CustomerDao customerDao;
    @InjectMocks
    CustomerService customerService;

    @Test
    void findAll() {
        var list = List.of(
                Customer.builder().id(UUID.randomUUID().toString())
                        .companyName("A").totalQuantity(10000.0).build(),
                Customer.builder().id(UUID.randomUUID().toString())
                        .companyName("B").totalQuantity(20000.0).build(),
                Customer.builder().id(UUID.randomUUID().toString())
                        .companyName("C").totalQuantity(30000.0).build(),
                Customer.builder().id(UUID.randomUUID().toString())
                        .companyName("D").totalQuantity(40000.0).build()
        );
        Mockito.when(this.customerDao.findAll()).thenReturn(list);
        assertThat(customerService.findAll().get().size()).isEqualTo(list.size());
    }

    @Test
    void findCustomerById() {
        var customer =
                Customer.builder().id(UUID.randomUUID().toString())
                                .companyName("A").totalQuantity(10000.0).build();
        Mockito.when(customerDao.existsById(any())).thenReturn(true);
        Mockito.when(customerDao.findById(any())).thenReturn(Optional.of(customer));
        assertThat(customerService.findCustomerById(customer.getId()).get().getId()).isEqualTo(customer.getId());
    }

    @Test
    void getTopCustomerInterval() {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(new Date());
        c2.setTime(new Date());
        c1.add(Calendar.YEAR, -10);
        c2.add(Calendar.YEAR, 10);
        Integer limit = 10;
        List<CustomerQuantity> cq = new ArrayList<>();
        Mockito
                .when(customerDao.getCustomersByDurationAndLimit(
                        any(),
                        any(),
                        any()
                )).thenReturn(Optional.of(cq));

        var resp = customerService.getTopCustomerInterval(10, c1.getTime(), c2.getTime());
        assertThat(resp.get().size()).isEqualTo(0);
    }
}