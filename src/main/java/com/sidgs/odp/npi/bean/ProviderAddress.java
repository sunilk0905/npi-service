package com.sidgs.odp.npi.bean;


import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

public class ProviderAddress  {

    public static final String BUSINESS_ADDRESS = "BUSINESS_ADDRESS ";
    public static final String PRACTIVE_ADDRESS = "PRACTICE_ADDRESS ";

    String line1;
    String line2 ;
    String city ;
    String state;

    @Indexed
    int postalCode ;
    long teleNumber ;
    long faxNumber ;

    @Indexed
    String addressType ;
    Date validFromDate ;
    Date validToDate ;

    public Date getValidFromDate() {
        return validFromDate;
    }

    public void setValidFromDate(Date validFromDate) {
        this.validFromDate = validFromDate;
    }

    public Date getValidToDate() {
        return validToDate;
    }

    public void setValidToDate(Date validToDate) {
        this.validToDate = validToDate;
    }

    public ProviderAddress(String addressType) {
        this.addressType = addressType;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(int postalCode) {
        this.postalCode = postalCode;
    }

    public long getTeleNumber() {
        return teleNumber;
    }

    public void setTeleNumber(long teleNumber) {
        this.teleNumber = teleNumber;
    }

    public long getFaxNumber() {
        return faxNumber;
    }

    public void setFaxNumber(long faxNumber) {
        this.faxNumber = faxNumber;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }
}
