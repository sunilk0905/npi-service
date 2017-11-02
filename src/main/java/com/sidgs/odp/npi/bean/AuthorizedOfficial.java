package com.sidgs.odp.npi.bean;


public class AuthorizedOfficial extends ProviderName {
    String titleOrPosition  ;
    String phoneNumber ;

    public String getTitleOrPosition() {
        return titleOrPosition;
    }

    public void setTitleOrPosition(String titleOrPosition) {
        this.titleOrPosition = titleOrPosition;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
