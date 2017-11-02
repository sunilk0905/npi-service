package com.sidgs.odp.npi.bean;


public class ProviderOrganization {

    String legalBusinessName ;
    String alternateOrgName ;
    String alternateOrgTypeCode ;

    public String getLegalBusinessName() {
        return legalBusinessName;
    }

    public void setLegalBusinessName(String legalBusinessName) {
        this.legalBusinessName = legalBusinessName;
    }

    public String getAlternateOrgName() {
        return alternateOrgName;
    }

    public void setAlternateOrgName(String alternateOrgName) {
        this.alternateOrgName = alternateOrgName;
    }

    public String getAlternateOrgTypeCode() {
        return alternateOrgTypeCode;
    }

    public void setAlternateOrgTypeCode(String alternateOrgTypeCode) {
        this.alternateOrgTypeCode = alternateOrgTypeCode;
    }
}
