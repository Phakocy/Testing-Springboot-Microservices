package com.countrymicroserviceproject;

import com.countrymicroserviceproject.bean.Country;
import com.countrymicroserviceproject.controller.CountryController;
import com.countrymicroserviceproject.service.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class) //Order of execution
@SpringBootTest(classes = {ControllerMockitoTest.class})
public class ControllerMockitoTest {

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    @Test
    @Order(1)
    public void test_getAllCountries(){

        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1,"Algeria","Algiers"));
        countries.add(new Country(2,"Ukraine","Kiev"));
        countries.add(new Country(3,"Russia","Moscow"));

        when(countryService.getAllCountries()).thenReturn(countries);
       ResponseEntity<List<Country>> outPut = countryController.getCountries();
       assertEquals(HttpStatus.OK, outPut.getStatusCode());
       assertEquals(3,outPut.getBody().size());

    }



}
