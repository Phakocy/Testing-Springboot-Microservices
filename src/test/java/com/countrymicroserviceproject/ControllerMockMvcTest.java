package com.countrymicroserviceproject;

import com.countrymicroserviceproject.bean.Country;
import com.countrymicroserviceproject.controller.CountryController;
import com.countrymicroserviceproject.service.CountryService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//TESTING THE URI'S i.e. THE ENDPOINTS
@ComponentScan(basePackages = "com.countrymicroserviceproject")
@AutoConfigureMockMvc
@ContextConfiguration
@SpringBootTest(classes = {ControllerMockMvcTest.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ControllerMockMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Mock
    CountryService countryService;

    @InjectMocks
    CountryController countryController;

    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders.standaloneSetup(countryController).build();
    }

    @Test @Order(1)
    public void test_getAllCountries() throws Exception {
        List<Country> countries = new ArrayList<>();
        countries.add(new Country(1,"Morocco","Rabat"));
        countries.add(new Country(2,"China","Beijing"));
        countries.add(new Country(3,"Australia","Canberra "));

        when(countryService.getAllCountries()).thenReturn(countries);

        this.mockMvc.perform(get("/getcountries"))
                .andExpect(status().isFound())
                .andDo(print());
    }
@Test @Order(2)
    public void test_getCountryById() throws Exception {

    Country country = new Country(4,"Scotland","Edinburgh");
    int countryId = 4;

    when(countryService.getCountryById(countryId)).thenReturn(country);

    this.mockMvc.perform(get("/getcountries/{id}",countryId)) //For Path Variable
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("id").value(countryId)) //For Eclipse IDE Use json path finder in google for the jsonPath()
            .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("Scotland"))
            .andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Edinburgh"))
            .andDo(print());
}

@Test @Order(3)
    public void test_getCountryByName() throws Exception {

        Country country = new Country(5,"Peru","Lima");
        String countryName = "Peru";

        when(countryService.getCountryByName(countryName)).thenReturn(country);

        this.mockMvc.perform(get("/getcountries/countryname").param("name", countryName)) //For Request Param
                .andExpect(status().isFound())
                .andExpect(MockMvcResultMatchers.jsonPath("id").value(5))
                .andExpect(MockMvcResultMatchers.jsonPath("countryName").value(countryName))
                .andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Lima"))
                .andDo(print());
}

@Test @Order(4)
    public void test_addCountry() throws Exception {

        Country country = new Country(6,"Brazil","Brasilia");

        when(countryService.addCountry(country)).thenReturn(country);

        //Convert to json format
    ObjectMapper mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(country);

    //MOCK REQUEST
        this.mockMvc.perform(post("/addcountry")
                .content(jsonBody)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andDo(print());
}

@Test @Order(5)
    public void test_updateCountry() throws Exception {

        Country country = new Country(7,"Malaysia","Kuala Lumpur");
       int countryId = 7;

       when(countryService.getCountryById(countryId)).thenReturn(country);
       when(countryService.updateCountry(country)).thenReturn(country);

    //Convert to json format
    ObjectMapper mapper = new ObjectMapper();
    String jsonBody = mapper.writeValueAsString(country);

    //MOCK REQUEST
    this.mockMvc.perform(put("/updatecountry/{id}",countryId)
                    .content(jsonBody)
                    .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(MockMvcResultMatchers.jsonPath("countryName").value("Malaysia"))
            .andExpect(MockMvcResultMatchers.jsonPath("countryCapital").value("Kuala Lumpur"))
            .andDo(print());
}

@Test @Order(6)
    public void test_removeCountry() throws Exception {

    Country country = new Country(8,"Switzerland","Bern");
    int countryId = 8;

    when(countryService.getCountryById(countryId)).thenReturn(country);

    this.mockMvc.perform(delete("/removecountry/{id}",countryId))
            .andExpect(status().isOk());

}

}
