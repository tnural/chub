package com.cleanhub.customertakehometask.controller;

import com.cleanhub.customertakehometask.dao.CustomerDao;
import com.cleanhub.customertakehometask.dto.TopCustomerIntervalDto;
import com.cleanhub.customertakehometask.entity.Customer;
import com.cleanhub.customertakehometask.mapper.CustomerQuantity;
import com.cleanhub.customertakehometask.service.CustomerService;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import javax.annotation.Resource;
import java.util.*;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
class CustomerControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Mock
    CustomerDao customerDao;
    @InjectMocks
    @Resource
    CustomerService customerService;

    @BeforeEach
    void init(){
        ReflectionTestUtils.setField(customerService, "customerDao", customerDao);
    }

    @Test
    void getAllCustomer() throws Exception {
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

        Mockito.when(customerDao.findAll()).thenReturn(list);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/customer"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(4)));

    }

    @Test
    void testGetTopNumberOfCustomerInterval() throws Exception {

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(new Date());
        c2.setTime(new Date());
        c1.add(Calendar.YEAR, -10);
        c2.add(Calendar.YEAR, 10);
        Integer limit = 10;
        CustomerQuantity cQ = new CustomerQuantity() {
            @Override
            public String getId() {
                return "ID";
            }

            @Override
            public Double getQuantity() {
                return 100.0;
            }
        };
        var cQList= List.of(cQ);
        Mockito
                .when(customerDao.getCustomersByDurationAndLimit(
                        any(),
                        any(),
                        any()
                )).thenReturn(Optional.of(cQList));

        Mockito.when(customerDao.findById(any())).thenReturn(Optional.ofNullable(Customer.builder().companyName("A").id("ID").build()));

        var dto = TopCustomerIntervalDto.builder()
                .from(new Date())
                .to(new Date())
                .limit(10);
        this.mockMvc
                .perform(
                        MockMvcRequestBuilders
                                .get("/customer/top")
                                .content(asJsonString(dto))
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string(containsString("A")));
    }

    public static String asJsonString(final Object obj) {
        try {
            var objMapper = new ObjectMapper();
            objMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            return objMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}