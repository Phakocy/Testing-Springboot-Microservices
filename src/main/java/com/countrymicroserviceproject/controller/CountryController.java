package com.countrymicroserviceproject.controller;

import com.countrymicroserviceproject.bean.Country;
import com.countrymicroserviceproject.bean.ResponseMessage;
import com.countrymicroserviceproject.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping()
public class CountryController {

    @Autowired
    CountryService countryService;

     @GetMapping("/getcountries")
        public ResponseEntity<List<Country>> getCountries(){
         try{
       List country = countryService.getAllCountries();
       return new ResponseEntity<List<Country>>(country,HttpStatus.FOUND);
    } catch (Exception e){
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);
         }
     }

    @GetMapping("/getcountries/{id}")
     public ResponseEntity<Country> getCountryById(@PathVariable int id){
       try {
           Country country = countryService.getCountryById(id);
       return new ResponseEntity<Country>(country, HttpStatus.OK);
       } catch (Exception e) {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND );
       }
    }

    @GetMapping("/getcountries/countryname")
        public ResponseEntity<Country> getCountryByName(@RequestParam(value = "name") String countryName){
        try{
        Country country = countryService.getCountryByName(countryName);
        return new ResponseEntity<Country>(country, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/addcountry")
        public Country insertCountry(@RequestBody Country country){
        return countryService.addCountry(country);
    }

    @PutMapping("/updatecountry/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") int id, @RequestBody Country country){
        try{
       Country countryFound  = countryService.getCountryById(id);

       countryFound.setCountryName(country.getCountryName());
       countryFound.setCountryCapital(country.getCountryCapital());
       Country updatedCountry = countryService.updateCountry(countryFound);
       return new ResponseEntity<Country>(updatedCountry,HttpStatus.OK);
    } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
    @DeleteMapping("/removecountry/{id}")
    public ResponseEntity<Country> removeCountry(@PathVariable(value = "id") int id){
        Country country = null;
        try{
            country = countryService.getCountryById(id);
             countryService.deleteCountry(id);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Country>(country, HttpStatus.OK);
    }
    }
