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
import static org.mockito.Mockito.verify;
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
       assertEquals(HttpStatus.FOUND, outPut.getStatusCode());
       assertEquals(3,outPut.getBody().size());

    }

@Test @Order(2)
    public void test_getCountryById(){
        Country country = new Country(4,"Argentina","Bueno Aires");
        int countryId = 4;
        when(countryService.getCountryById(countryId)).thenReturn(country);

        ResponseEntity<Country> outPut = countryController.getCountryById(countryId);

        assertEquals(HttpStatus.OK, outPut.getStatusCode());
        assertEquals(countryId, outPut.getBody().getId());
}

@Test @Order(3)
    public void test_getCountryByName(){
        Country country = new Country(5,"Iran","Baghdad");

    String countryName = "Iran";
    when(countryService.getCountryByName(countryName)).thenReturn(country);
    ResponseEntity<Country> outPut = countryController.getCountryByName(countryName);
    assertEquals(HttpStatus.FOUND, outPut.getStatusCode());
    assertEquals(countryName,outPut.getBody().getCountryName());
}

@Test @Order(4)
    public void test_addCountry(){
    Country country = new Country(6,"Japan","Tokyo");

    when(countryService.addCountry(country)).thenReturn(country);
    ResponseEntity<Country> outPut = countryController.insertCountry(country);
    assertEquals(HttpStatus.CREATED, outPut.getStatusCode());
    assertEquals(country, outPut.getBody());
}

@Test @Order(5)
    public  void test_UpdateCountry(){
    Country country = new Country(6,"Japan","Tokyo");

    int countryId = 6;
    when(countryService.getCountryById(countryId)).thenReturn(country);
    when(countryService.updateCountry(country)).thenReturn(country);
    ResponseEntity<Country> outPut = countryController.updateCountry(countryId,country);
    assertEquals(HttpStatus.OK, outPut.getStatusCode());
    assertEquals(countryId,outPut.getBody().getId());
    assertEquals(country.getCountryName(), outPut.getBody().getCountryName()); //same with assertEquals("Japan", outPut.getBody().getCountryName());
    assertEquals("Tokyo", outPut.getBody().getCountryCapital()); //assertEquals(country.getCountryCapital(), outPut.getBody().getCountryName());
}

@Test @Order(6)
    public void test_removeCountry(){
    Country country = new Country(7,"Afghanistan","Kabul");
    int countryId = 7;

    when(countryService.getCountryById(countryId)).thenReturn(country);
    ResponseEntity<Country> removedCountry = countryController.removeCountry(countryId);
    assertEquals(HttpStatus.OK, removedCountry.getStatusCode());
}
}
 