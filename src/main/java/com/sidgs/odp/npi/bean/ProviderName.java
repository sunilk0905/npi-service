package com.sidgs.odp.npi.bean;

public class ProviderName {
    String last ;
    String first ;
    String middle ;
    String prefix ;
    String suffix ;
    String credential ;
    String lastNameType ;

    public ProviderName() {
    }

    public ProviderName(String lastNameType) {
        this.lastNameType = lastNameType;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getMiddle() {
        return middle;
    }

    public void setMiddle(String middle) {
        this.middle = middle;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getLastNameType() {
        return lastNameType;
    }

    public void setLastNameType(String lastNameType) {
        this.lastNameType = lastNameType;
    }
}
