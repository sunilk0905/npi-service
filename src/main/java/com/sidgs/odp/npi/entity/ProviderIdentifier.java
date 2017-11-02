package com.sidgs.odp.npi.entity;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Document(collection = "provider-identifier")
public class ProviderIdentifier extends BaseEntity {

    @Indexed
    int npi;
    @Indexed
    int npiType ;

    @Indexed
    String provIdentifier ;
    String provIdentifierCode ;
    @Indexed
    String provIdentifierState ;
    String provIdentifierIssuedBy ;

    Date validfrom ;
    Date validTo ;

    public Date getValidfrom() {
        return validfrom;
    }

    public void setValidfrom(Date validfrom) {
        this.validfrom = validfrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }


    public ProviderIdentifier() {
    }

    public ProviderIdentifier(int npi, int npiType, String provIdentifier, String provIdentifierCode, String provIdentifierState, String provIdentifierIssuedBy) {
        this.npi = npi;
        this.npiType = npiType;
        this.provIdentifier = provIdentifier;
        this.provIdentifierCode = provIdentifierCode;
        this.provIdentifierState = provIdentifierState;
        this.provIdentifierIssuedBy = provIdentifierIssuedBy;
    }

    public String getProvIdentifier() {
        return provIdentifier;
    }

    public void setProvIdentifier(String provIdentifier) {
        this.provIdentifier = provIdentifier;
    }

    public String getProvIdentifierCode() {
        return provIdentifierCode;
    }

    public void setProvIdentifierCode(String provIdentifierCode) {
        this.provIdentifierCode = provIdentifierCode;
    }

    public String getProvIdentifierState() {
        return provIdentifierState;
    }

    public void setProvIdentifierState(String provIdentifierState) {
        this.provIdentifierState = provIdentifierState;
    }

    public String getProvIdentifierIssuedBy() {
        return provIdentifierIssuedBy;
    }

    public void setProvIdentifierIssuedBy(String provIdentifierIssuedBy) {
        this.provIdentifierIssuedBy = provIdentifierIssuedBy;
    }

    public int getNpi() {
        return npi;
    }

    public void setNpi(int npi) {
        this.npi = npi;
    }

    public int getNpiType() {
        return npiType;
    }

    public void setNpiType(int npiType) {
        this.npiType = npiType;
    }

    @Override
    public String toString() {
        return "ProviderIdentifier{" +
                "npi=" + npi +
                ", npiType=" + npiType +
                ", provIdentifier='" + provIdentifier + '\'' +
                ", provIdentifierCode='" + provIdentifierCode + '\'' +
                ", provIdentifierState='" + provIdentifierState + '\'' +
                ", provIdentifierIssuedBy='" + provIdentifierIssuedBy + '\'' +
                '}';
    }
}
