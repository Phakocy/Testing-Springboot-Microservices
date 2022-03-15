package com.countrymicroserviceproject.service;

import com.countrymicroserviceproject.CountryRepository;
import com.countrymicroserviceproject.bean.Country;
import com.countrymicroserviceproject.bean.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CountryService {

    CountryRepository countryRepository;

    @Autowired
    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List getAllCountries(){

        return countryRepository.findAll();
    }

    public Country getCountryById(int id){

        List <Country> countries = countryRepository.findAll();

        Country country = null;
        for (Country con: countries) {
            if(con.getId() == id)
                country = con;
        }
      return country;
    }

    public Country getCountryByName(String countryName){

     List<Country>  countries = countryRepository.findAll();

     Country country = null;
        for (Country con: countries) {
            if (con.getCountryName().equalsIgnoreCase(countryName)) {
                country = con;
            }

        }
        return country;
    }

    public Country addCountry(Country country){

        country.setId(getMaxId());
   countryRepository.save(country);
        return country ;
    }

    public int getMaxId(){
       return countryRepository.findAll().size()+1;
    }

    public Country updateCountry(Country country){
        countryRepository.save(country);
        return country;
    }

    public ResponseMessage deleteCountry(int id){

        countryRepository.deleteById(id);
        ResponseMessage rm = new ResponseMessage();
        rm.setMsg("Country Deleted...");
        rm.setId(id);
        return rm;
    }
}
