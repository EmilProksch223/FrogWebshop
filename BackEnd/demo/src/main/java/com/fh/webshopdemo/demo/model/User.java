package com.fh.webshopdemo.demo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;


@Entity(name="user")
public class User {
    
    @Id
    @GeneratedValue
    @Column(name="id")
    private final Long id;


    @Column(name="gender")
    private String gender;

    @NotBlank(message = "Vorname darf nicht leer sein")
    @Column(name="firstName")
    private String firstName;


    @NotBlank(message = "Nachname darf nicht leer sein")
    @Column(name="lastName")
    private String lastName;

    @NotBlank(message = "E-Mail darf nicht leer sein")
    @Email(message = "E-Mail muss existieren")
    @Column(name="eMail")
    private String eMail;


    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", 
    message = "Das Passwort muss mindestens 8 Zeichen lang sein, mindestens eine Zahl, mindestens einen Großbuchstaben und mindestens einen Kleinbuchstaben enthalten")
    @Column(name="password")
    private String password;

    @NotBlank(message = "Adresse darf nicht leer sein")
    @Column(name="address")
    private String address;

    @Column(name="address2")
    private String address2;

    @NotBlank(message = "Stadt darf nicht leer sein")
    @Column(name="city")
    private String city;

    @NotBlank(message = "Bezirk darf nicht leer sein")
    @Column(name="district")
    private String district;

    @NotBlank(message = "Land darf nicht leer sein")
    @Column(name="country")
    private String country;

    @Column(name="userRights")
    private String userRights;


    public User(){
        this.id = null;
        this.gender = null;
        this.firstName = null;
        this.lastName = null;
        this.eMail = null;
        this.password = null;
        this.address = null;
        this.address2 = null;
        this.city = null;
        this.district = null;
        this.country = null;
        this.userRights = null;
    }




    public User(Long id, String gender, String firstName , String lastName, String eMail , String password, String address, String address2, String city, String district, String country, String userRights) {
        this.id = id;
        this.gender = gender;
        this.firstName = firstName;
        this.lastName = lastName;
        this.eMail = eMail;
        this.password = password;
        this.address = address;
        this.address2 = address2;
        this.city = city;
        this.district = district;
        this.country = country;
        this.userRights = userRights;
    }



    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getUserRights() {
        return userRights;
    }

    public void setUserRights(String userRights) {
        this.userRights = userRights;
    }

}
