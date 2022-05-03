package com.cleanhub.customertakehometask.service;

import com.cleanhub.customertakehometask.entity.Customer;
import com.cleanhub.customertakehometask.entity.Logo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Collectors;

@SpringBootTest
@TestPropertySource(properties = "app.scheduling.enable=false")
public class ParserTest {

    @Test
    void gsonParserTest() {
        String input1 = "   {\n" +
                "\n" +
                "    \"uuid\": \"e73795c3-4da6-4aef-be1d-7bc3c8bd8ae8\",\n" +
                "    \"state\": \"IN_PROGRESS\",\n" +
                "    \"createdAt\": \"2021-03-05T15:39:26.504Z\",\n" +
                "    \"lastModifiedAt\": \"2022-03-28T02:56:13.034553Z\",\n" +
                "    \"companyName\": \"Vuori\",\n" +
                "    \"companyUrl\": \"https://vuoriclothing.com/\",\n" +
                "    \"companyLink\": \"https://vuoriclothing.com/\",\n" +
                "    \"contractStartDate\": \"2021-01-01T15:35:00Z\",\n" +
                "    \"landingPageRoute\": \"vuori\",\n" +
                "    \"landingPageMode\": \"RELEASED\",\n" +
                "    \"quantity\": 124951.0,\n" +
                "    \"quantityUnit\": \"POUND\",\n" +
                "    \"recoveredQuantity\": 64741.305208,\n" +
                "    \"recoveredPercentage\": 51.81335500156061,\n" +
                "    \"contracts\": [\n" +
                "        {\n" +
                "            \"uuid\": \"aee0bfc8-6e3d-4b08-a519-9a15cc0fff88\",\n" +
                "            \"type\": \"INITIAL\",\n" +
                "            \"createdAt\": \"2021-11-23T09:30:18.444514Z\",\n" +
                "            \"startDate\": \"2021-01-01T15:35:00Z\",\n" +
                "            \"endDate\": \"2021-12-31T23:59:59.999Z\",\n" +
                "            \"period\": \"YEARLY\",\n" +
                "            \"quantity\": 28651.0,\n" +
                "            \"recoveredQuantity\": 28651.0,\n" +
                "            \"isFulfilled\": true,\n" +
                "            \"quantityUnit\": \"POUND\",\n" +
                "            \"salesforceOpportunityExternalId\": null\n" +
                "        },\n" +
                "        {\n" +
                "            \"uuid\": \"23c509ea-d982-4b07-8acb-e746a17927e2\",\n" +
                "            \"type\": \"RENEWAL\",\n" +
                "            \"createdAt\": \"2022-01-31T10:41:40.731235Z\",\n" +
                "            \"startDate\": \"2022-01-01T00:00:00Z\",\n" +
                "            \"endDate\": null,\n" +
                "            \"period\": \"YEARLY\",\n" +
                "            \"quantity\": 96300.0,\n" +
                "            \"recoveredQuantity\": 36090.305208,\n" +
                "            \"isFulfilled\": false,\n" +
                "            \"quantityUnit\": \"POUND\",\n" +
                "            \"salesforceOpportunityExternalId\": null\n" +
                "        }\n" +
                "    ]}";

        String input2 ="[\n" +
                "    {\n" +
                "        \"logo\": {\n" +
                "            \"uuid\": \"4b3b2958-6744-4f26-866b-fab7ccb828fc\",\n" +
                "            \"directLink\": \"https://cleanhub-marketplace-pictures.s3.eu-central-1.amazonaws.com/admin/37129aad959042d98c8c4292b35636d3dc771a51bcd738ba66ed7c9b02045f62?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220502T084355Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3599&X-Amz-Credential=AKIAYGJI4AGS2XJ5VJNO%2F20220502%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Signature=fdc0f76e8bbf92afc21f319ce2dc7456b4b64fb1f5330fc93dd3ffe0fc2f869c\",\n" +
                "            \"thumbnailDirectLink\": \"https://cleanhub-marketplace-pictures.s3.eu-central-1.amazonaws.com/admin/aecbc2659bafd27e80e1f582ecc5c5e36c339a9985e9ef5588ab1e818bab0d6f?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220502T084355Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAYGJI4AGS2XJ5VJNO%2F20220502%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Signature=624dd964a5da4032e90e10a0dfc46aaf774c87fdd4f4f930d674d739b1a51ba4\",\n" +
                "            \"fileName\": \"Logo_s_r_12befb49-93a5-4a50-91ce-9ba89b56353a_394x.png\",\n" +
                "            \"size\": 7752\n" +
                "        },\n" +
                "        \"landingPageRoute\": \"foamie\",\n" +
                "        \"featured\": false\n" +
                "    },\n" +
                "    {\n" +
                "        \"logo\": {\n" +
                "            \"uuid\": \"be5e81e3-68a7-4930-891e-2ce02dd8538f\",\n" +
                "            \"directLink\": \"https://cleanhub-marketplace-pictures.s3.eu-central-1.amazonaws.com/admin/7e888a8a0a548b1c93105fbd36fd6675363db941ecee3ba7b597169763b4a2eb?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220502T084355Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAYGJI4AGS2XJ5VJNO%2F20220502%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Signature=c6a063e287fac16500b4658a56748244da3a4acd64940944ff5dc6a6c76668e4\",\n" +
                "            \"thumbnailDirectLink\": \"https://cleanhub-marketplace-pictures.s3.eu-central-1.amazonaws.com/admin/491dbeb0b8091fac3564b991006aa0c695ac453896b0c5dcf847c1ee7190af31?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220502T084355Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAYGJI4AGS2XJ5VJNO%2F20220502%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Signature=c8d853e4fbb3677252940ab7750929e1030c2a5aadff83c32472bea75ff2ca06\",\n" +
                "            \"fileName\": \"share1.png\",\n" +
                "            \"size\": 19000\n" +
                "        },\n" +
                "        \"landingPageRoute\": \"share\",\n" +
                "        \"featured\": false\n" +
                "    },\n" +
                "    {\n" +
                "        \"logo\": {\n" +
                "            \"uuid\": \"201ee19a-a37f-4970-8c40-0c83d5be87d7\",\n" +
                "            \"directLink\": \"https://cleanhub-marketplace-pictures.s3.eu-central-1.amazonaws.com/admin/428120562669277072c6e09c11f1f924838231eab0094486165c9c74e80b22af?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220502T084355Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAYGJI4AGS2XJ5VJNO%2F20220502%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Signature=da1e6bc84fd19ec1d1b95cdf8e7f1d0dd910d2c90d169f926d6fee43fc33f460\",\n" +
                "            \"thumbnailDirectLink\": \"https://cleanhub-marketplace-pictures.s3.eu-central-1.amazonaws.com/fuchs_gmbh_%26_co._kg/c2eb8dc211afb1021fbf8074b0e3e96ce269923cbbcbe959c0aa6200b65292d7?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20220502T084355Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=AKIAYGJI4AGS2XJ5VJNO%2F20220502%2Feu-central-1%2Fs3%2Faws4_request&X-Amz-Signature=656548815addf2147b441defd4bf4dae5ad03488d63fc88ac0490489d231e8bb\",\n" +
                "            \"fileName\": \"Fuchs-Logo-Colour-CMYK-27.12.16.png\",\n" +
                "            \"size\": 28483\n" +
                "        },\n" +
                "        \"landingPageRoute\": \"fuchs\",\n" +
                "        \"featured\": true\n" +
                "    }\n" +
                "]";

        Gson gson = new Gson();
        Type userListType = new TypeToken<Customer>(){}.getType();
        Type logoType = new TypeToken<ArrayList<Logo>>(){}.getType();
        ArrayList<Logo> userArray = gson.fromJson(input2, logoType);
        Customer customer = gson.fromJson(input1, userListType);
        System.out.println(customer);
        userArray.forEach(System.out::println);
    }

    @Test
    void restApiTest(){
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
    }
}
