package com.cleanhub.customertakehometask.job;

import com.cleanhub.customertakehometask.dao.CustomerDao;
import com.cleanhub.customertakehometask.entity.Customer;
import com.cleanhub.customertakehometask.entity.Logo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Configuration
@EnableScheduling
@ConditionalOnProperty(
        value = "app.scheduling.enable", havingValue = "true", matchIfMissing = true
)
public class CustomerDetailFetcherJob {
    @Autowired
    CustomerDao customerDao;

    @PostConstruct
    public void onStartup() {
        execute();
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void execute() {
        System.out.println("job has started");
        final String companyListURI = "https://marketplace.cleanhub.com/api/public/orders/logos";
        String contractsURI = "";
        var customerList = new ArrayList<Customer>();
        RestTemplate restTemplate = new RestTemplate();
        String result = restTemplate.getForObject(companyListURI, String.class);
        Gson gson = new Gson();
        Type logoType = new TypeToken<ArrayList<Logo>>() {
        }.getType();
        Type customerListType = new TypeToken<Customer>() {
        }.getType();
        ArrayList<Logo> logoArray = gson.fromJson(result, logoType);
        var companyNames = logoArray.stream().map(i -> i.getLandingPageRoute()).collect(Collectors.toList());
        for (String companyName : companyNames) {
            contractsURI = "https://marketplace.cleanhub.com/api/public/orders?route=" + companyName;
            result = restTemplate.getForObject(contractsURI, String.class);
            Customer customer = gson.fromJson(result, customerListType);
            customerList.add(customer);
        }
        System.out.println("fetched customer list");
        customerList.forEach(System.out::println);
        customerList.forEach(x -> {
                            x
                            .getContractList()
                            .forEach(y -> y.setCustomer(x));
                });
        this.customerDao.saveAll(customerList);
    }
}
