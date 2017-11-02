package com.sidgs.odp.npi.entity;

import com.sidgs.odp.npi.bean.ProviderLicense;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

import java.util.Date;

@Document ( collection = "provider-taxonomy")
@Component
public class ProviderTaxonomy extends BaseEntity {

    @Indexed
   int npi;
    @Indexed
    int type ;

    @Indexed
    String taxonomyCode ;
    ProviderLicense license ;
    boolean primary = false ;

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


    public ProviderTaxonomy() {
    }

    public ProviderTaxonomy(int npi, int type, String taxonomyCode) {
        this.npi = npi;
        this.type = type;
        this.taxonomyCode = taxonomyCode;
    }

    public int getNpi() {
        return npi;
    }

    public void setNpi(int  npi) {
        this.npi = npi;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getTaxonomyCode() {
        return taxonomyCode;
    }

    public void setTaxonomyCode(String taxonomyCode) {
        this.taxonomyCode = taxonomyCode;
    }

    public ProviderLicense getLicense() {
        return license;
    }

    public void setLicense(ProviderLicense license) {
        this.license = license;
    }

    public boolean isPrimary() {
        return primary;
    }

    public void setPrimary(boolean primary) {
        this.primary = primary;
    }

    public void setLicense ( String licNumber ,String licState) {
        ProviderLicense license =  new ProviderLicense();
        license.setNumber(licNumber);
        license.setState(licState);
        this.license = license;
    }
}
