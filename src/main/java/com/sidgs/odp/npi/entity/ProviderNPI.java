package com.sidgs.odp.npi.entity;

import com.sidgs.odp.npi.bean.AuthorizedOfficial;
import com.sidgs.odp.npi.bean.ProviderAddress;
import com.sidgs.odp.npi.bean.ProviderName;
import com.sidgs.odp.npi.bean.ProviderOrganization;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Document (collection = "provider-npi")
public class ProviderNPI extends BaseEntity {


    @Indexed
    int providerNPI ;

    @Indexed
    int providerNPIEntityType ;

    @Indexed
    int replacementProviderNPI ;

    String providerEnumerationDate ;
    
    String providerLastUpdateDate ;
    
    String recordlastUpdate ;

    String gender   ;
    String isSoleProprietor ;
    String isSubPart ;

    String npiDeactivationDate;
    String npiDeactivationReason ;
    String npiReactivationDate ;


    String parentOrgLBN ;

    @Indexed
    String parentOrgTIN ;

    @Indexed
    String providerEIN ;

    ProviderName providerName = new ProviderName("LEGAL") ;
    ProviderName alternateProviderName = new ProviderName("ALTERNATE");

    List<ProviderAddress> businessAddress = new ArrayList<ProviderAddress>();
    List<ProviderAddress> practiceAddress = new ArrayList<ProviderAddress>();

    List<String> providerTaxonomyGroup = new ArrayList<String>();

    AuthorizedOfficial authorizedOfficial ;

    public AuthorizedOfficial getAuthorizedOfficial() {
        return authorizedOfficial;
    }

    public void setAuthorizedOfficial(AuthorizedOfficial authorizedOfficial) {
        this.authorizedOfficial = authorizedOfficial;
    }

    ProviderOrganization organization ;

    public ProviderOrganization getOrganization() {
        return organization;
    }

    public void setOrganization(ProviderOrganization organization) {
        this.organization = organization;
    }

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

    public void addBusinessAddress(ProviderAddress address) {
        getBusinessAddress().add(address);
    }

    public void addPracticeAddress(ProviderAddress address){
        practiceAddress.add(address);
    }

    public int getProviderNPI() {
        return providerNPI;
    }

    public void setProviderNPI(int providerNPI) {
        this.providerNPI = providerNPI;
    }

    public int getProviderNPIEntityType() {
        return providerNPIEntityType;
    }

    public void setProviderNPIEntityType(int providerNPIEntityType) {
        this.providerNPIEntityType = providerNPIEntityType;
    }

    public int getReplacementProviderNPI() {
        return replacementProviderNPI;
    }

    public void setReplacementProviderNPI(int replacementProviderNPI) {
        this.replacementProviderNPI = replacementProviderNPI;
    }

    public String getProviderEnumerationDate() {
        return providerEnumerationDate;
    }

    public void setProviderEnumerationDate(String providerEnumerationDate) {
        this.providerEnumerationDate = providerEnumerationDate;
    }
    
    public String getProviderLastUpdateDate() {
		return providerLastUpdateDate;
	}

	public void setProviderLastUpdateDate(String providerLastUpdateDate) {
		this.providerLastUpdateDate = providerLastUpdateDate;
	}

	public String getRecordlastUpdate() {
        return recordlastUpdate;
    }

    public void setRecordlastUpdate(String recordlastUpdate) {
        this.recordlastUpdate = recordlastUpdate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getIsSoleProprietor() {
        return isSoleProprietor;
    }

    public void setIsSoleProprietor(String isSoleProprietor) {
        this.isSoleProprietor = isSoleProprietor;
    }

    public String getIsSubPart() {
        return isSubPart;
    }

    public void setIsSubPart(String isSubPart) {
        this.isSubPart = isSubPart;
    }

    public String getNpiDeactivationDate() {
        return npiDeactivationDate;
    }

    public void setNpiDeactivationDate(String npiDeactivationDate) {
        this.npiDeactivationDate = npiDeactivationDate;
    }

    public String getNpiDeactivationReason() {
        return npiDeactivationReason;
    }

    public void setNpiDeactivationReason(String npiDeactivationReason) {
        this.npiDeactivationReason = npiDeactivationReason;
    }

    public String getNpiReactivationDate() {
        return npiReactivationDate;
    }

    public void setNpiReactivationDate(String npiReactivationDate) {
        this.npiReactivationDate = npiReactivationDate;
    }

    public String getParentOrgLBN() {
        return parentOrgLBN;
    }

    public void setParentOrgLBN(String parentOrgLBN) {
        this.parentOrgLBN = parentOrgLBN;
    }

    public String getParentOrgTIN() {
        return parentOrgTIN;
    }

    public void setParentOrgTIN(String parentOrgTIN) {
        this.parentOrgTIN = parentOrgTIN;
    }

    public String getProviderEIN() {
        return providerEIN;
    }

    public void setProviderEIN(String providerEIN) {
        this.providerEIN = providerEIN;
    }

    public ProviderName getProviderName() {
        return providerName;
    }

    public void setProviderName(ProviderName providerName) {
        this.providerName = providerName;
    }

    public ProviderName getAlternateProviderName() {
        return alternateProviderName;
    }

    public void setAlternateProviderName(ProviderName alternateProviderName) {
        this.alternateProviderName = alternateProviderName;
    }


    public List<ProviderAddress> getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(List<ProviderAddress> businessAddress) {
        this.businessAddress = businessAddress;
    }

    public List<ProviderAddress> getPracticeAddress() {
        return practiceAddress;
    }

    public void setPracticeAddress(List<ProviderAddress> practiceAddress) {
        this.practiceAddress = practiceAddress;
    }

    public List<String> getProviderTaxonomyGroup() {
        return providerTaxonomyGroup;
    }

    public void setProviderTaxonomyGroup(List<String> providerTaxonomyGroup) {
        this.providerTaxonomyGroup = providerTaxonomyGroup;
    }

}
