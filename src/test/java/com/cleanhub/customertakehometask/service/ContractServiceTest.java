package com.cleanhub.customertakehometask.service;

import com.cleanhub.customertakehometask.dao.ContractDao;
import com.cleanhub.customertakehometask.entity.Contract;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@TestPropertySource(properties = "app.scheduling.enable=false")
class ContractServiceTest {
    @InjectMocks
    ContractService contractService;
    @Mock
    ContractDao contractDao;
    @Test
    void getAllContracts() {
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
        var expected = contractService.getAllContracts();
        assertThat(expected.size()).isEqualTo(3);
    }
}