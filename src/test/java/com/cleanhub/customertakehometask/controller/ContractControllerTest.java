package com.cleanhub.customertakehometask.controller;

import com.cleanhub.customertakehometask.dao.ContractDao;
import com.cleanhub.customertakehometask.entity.Contract;
import com.cleanhub.customertakehometask.service.ContractService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = "app.scheduling.enable=false")
class ContractControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    ContractDao contractDao;

    @InjectMocks
    @Resource
    ContractService contractService;

    @BeforeEach
    void init(){
        ReflectionTestUtils.setField(contractService, "contractDao", contractDao);
    }

    @Test
    void getAllContracts() throws Exception {
        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();
        c1.setTime(new Date());
        c2.setTime(new Date());
        c1.add(Calendar.YEAR, -10);
        c2.add(Calendar.YEAR, 10);
        var contractList = List.of(
                Contract.builder().id(UUID.randomUUID().toString())
                        .startDateTime(c1.getTime()).endDateTime(c2.getTime())
                        .quantity(100.0).recoveredQuantity(50.0).isFullfilled(true).build(),
                Contract.builder().id(UUID.randomUUID().toString())
                        .startDateTime(c1.getTime()).endDateTime(c2.getTime())
                        .quantity(200.0).recoveredQuantity(100.0).isFullfilled(true).build(),
                Contract.builder().id(UUID.randomUUID().toString())
                        .startDateTime(c1.getTime()).endDateTime(c2.getTime())
                        .quantity(300.0).recoveredQuantity(200.0).isFullfilled(false).build()
        );

        Mockito.when(contractDao.findAll()).thenReturn(contractList);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/contract"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$", hasSize(3)));
    }
}