package com.countrymicroserviceproject.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Country")
public class Country  {

     @Id
     @Column(name = "Id")
     int id;

     @Column(name = "country_name")
     String countryName;

     @Column(name = "capital")
     String countryCapital;
}
