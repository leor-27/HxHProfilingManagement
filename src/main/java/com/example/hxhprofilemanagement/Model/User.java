package com.example.hxhprofilemanagement.Model;

import javafx.scene.image.Image;
import java.time.LocalDate;
import java.time.Period;

public class User {
    private int id;
    private Image image;
    private String givenName;
    private String middleName;
    private String lastName;
    private String gender;
    private String nenType;
    private String affiliation;
    private LocalDate birthdate;

    public User(int id, Image image, String givenName, String middleName,
                String lastName, LocalDate birthdate, String gender, String nenType,
                String affiliation) {
        this.id = id;
        this.image = image;
        this.givenName = givenName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.gender = gender;
        this.nenType = nenType;
        this.affiliation = affiliation;
    }

    public int getId() {
        return id;
    }

    public Image getImage() {
        return image;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getAge() {
        if (birthdate == null) return "";
        return String.valueOf(Period.between(birthdate, LocalDate.now()).getYears());
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public String getGender() {
        return gender;
    }

    public String getNenType() {
        return nenType;
    }

    public String getAffiliation() {
        return affiliation;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setBirthdate(LocalDate birthdate) {
        this.birthdate = birthdate;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setNenType(String nenType) {
        this.nenType = nenType;
    }

    public void setAffiliation(String affiliation) {
        this.affiliation = affiliation;
    }
}