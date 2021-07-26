package com.example.hamiltontevin_ce04.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ContactInfo implements Serializable {
    private final String mFirstName;
    private final String mLastName;
    private final String mMiddleName;


    private final ArrayList<String> numberList;
    private final String contactImage;

    public ContactInfo(String mFirstName,String mLastName,String mMiddleName, ArrayList<String> numberList, String contactImage) {
        this.mFirstName = mFirstName;
        this.mLastName = mLastName;
        this.mMiddleName = mMiddleName;

        this.numberList = numberList;
        this.contactImage = contactImage;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public String getMiddleName() {
        return mMiddleName;
    }

    public ArrayList<String> getNumberList() {
        return numberList;
    }

    public String getContactImage() {
        return contactImage;
    }
}
