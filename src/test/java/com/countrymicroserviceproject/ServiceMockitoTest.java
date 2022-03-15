package com.countrymicroserviceproject;

import com.countrymicroserviceproject.bean.Country;
import com.countrymicroserviceproject.service.CountryService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = {ServiceMockitoTest.class})
public class ServiceMockitoTest {
    @Mock
    CountryRepository countryRepository;
    @InjectMocks
    CountryService countryService;

   // public List<Country> countries;

    @Test
    @Order(1) //Order of execution
    public void test_getAllCountries(){
        Country ghana = new Country(1,"Ghana", "Accra");
        Country canada = new Country(2,"Canada", "Toronto");
        Country usa = new Country(3,"USA","Washington");

        List<Country> countries = new ArrayList<Country>();
        countries.add(ghana);
        countries.add(canada);
        countries.add(usa);
        countries.add(new Country(1, "Nigeria", "Abuja")); //You can also add like this

        when(countryRepository.findAll()).thenReturn(countries); //MOCKING

        assertEquals(4, countryService.getAllCountries().size()); //We have 4 countries above
    }

    @Test
    @Order(2)
    public void test_getCountryBYId(){
        List<Country> countries = new ArrayList<Country>();
        countries.add(new Country(1,"Portugal","Lisbon"));
        countries.add(new Country(2,"France","Paris"));

        //same as above, just another method of add to the list
        Country italy = new Country(1,"Italy","Rome");
        Country france = new Country(2,"France","Paris");
        countries.add(italy);
        countries.add(france);
        int countryId  = 1;

        when(countryRepository.findAll()).thenReturn(countries); //MOCKING
       assertEquals(countryId, countryService.getCountryById(countryId).getId());
    }

    @Test
    @Order(3)
    public void test_getCountryName(){

        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1,"India","Delhi"));
        countries.add(new Country(2,"Mozambique","Maputo"));

        String countryName = "India";
        when(countryRepository.findAll()).thenReturn(countries);
        assertEquals(countryName, countryService.getCountryByName(countryName).getCountryName());

    }

    @Test @Order(4)
    public void test_addCountry(){
        Country country = new Country(6,"Turkey","Instanbul");

        when(countryRepository.save(country)).thenReturn(country);
       assertEquals(country, countryService.addCountry(country));
    }

    @Test @Order(5)
    public void test_updateCountry(){

        Country country = new Country(5,"Germany","Berlin");
        when(countryRepository.save(country)).thenReturn(country);
       assertEquals(country, countryService.updateCountry(country));
    }
}

//"When and then" is used only when the mock is returning something for us,
// Instead use verify(countryRepository, times(1).delete(country)

//    @Test @Order(6)
//    public void test_deleteCountry(){
//        Country country = new Country(7,"Ireland","Dublin");
//
//        countryService.deleteCountry(country);
//        verify(countryRepository, times(1).delete(country))
//
//    } NOT WORKING
