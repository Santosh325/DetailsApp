package com.example.detailsapp.database;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "details")
public class DetailsEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String fathersName;
    private String phoneNumber;
    private String address;
    @Ignore
    public DetailsEntry(String name, String fathersName, String phoneNumber, String address) {
        this.name = name;
        this.fathersName = fathersName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public DetailsEntry(int id, String name, String fathersName, String phoneNumber, String address) {
        this.id = id;
        this.name = name;
        this.fathersName = fathersName;
        this.phoneNumber = phoneNumber;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFathersName() {
        return fathersName;
    }

    public void setFathersName(String fathersName) {
        this.fathersName = fathersName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
