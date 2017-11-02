package com.sidgs.odp.npi.util;


import com.sidgs.odp.npi.bean.ProviderAddress;
import com.sidgs.odp.npi.bean.ProviderName;
import com.sidgs.odp.npi.bean.ProviderOrganization;
import com.sidgs.odp.npi.entity.ProviderNPI;
import com.sidgs.odp.npi.error.InvalidNPITypeException;
import com.mongodb.DBObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class NPIDataLoaderUtil {

    Log logger = LogFactory.getLog(NPIDataLoaderUtil.class);

    public boolean setBusinessAddress(DBObject dbObject, ProviderNPI providerNPI) {
        String[] keys = {
                "Provider First Line Business Mailing Address" ,
                "Provider Second Line Business Mailing Address" ,
                "Provider Business Mailing Address City Name" ,
                "Provider Business Mailing Address State Name",
                "Provider Business Mailing Address Postal Code",
                "Provider Business Mailing Address Telephone Number" ,
                "Provider Business Mailing Address Fax Number"
        };

        if(dbObject==null)
            return false;
        ProviderAddress address = new ProviderAddress(ProviderAddress.BUSINESS_ADDRESS);
        try {

            buildProviderAddress(dbObject, keys, address);
            providerNPI.getBusinessAddress().add(address);

        }catch (Exception e) {
            //TODO catch content validation exception
            e.printStackTrace();
            return false;
        }
            return true ;
    }

    private void buildProviderAddress(DBObject dbObject, String[] keys, ProviderAddress address) {
        Object obj ;
        obj  = dbObject.get(keys[0]);
        address.setLine1((obj instanceof String)?(String)obj:null);
        obj =dbObject.get(keys[1]);
        address.setLine2((obj instanceof String)?(String)obj:null);
        obj = dbObject.get(keys[2]);
        address.setCity((obj instanceof String)?(String)obj:null);
        obj = dbObject.get(keys[3]);
        address.setState((obj instanceof String)?(String)obj:null);
        obj=dbObject.get(keys[4]);
        if(obj instanceof Integer)address.setPostalCode((Integer)obj);
        else if(isValidNumber(obj))address.setPostalCode(Integer.parseInt((String)obj));
        else address.setPostalCode(0);
        obj=dbObject.get(keys[5]);
        if(obj instanceof Long)address.setTeleNumber((Long)obj);
        else if(isValidNumber(obj))address.setTeleNumber(Long.parseLong((String)obj));
        else address.setTeleNumber(0);
        obj=dbObject.get(keys[6]);
        if(obj instanceof Long)address.setFaxNumber((Long)obj);
        else if(isValidNumber(obj))address.setFaxNumber(Long.parseLong((String)obj));
        else address.setFaxNumber(0);

    }

    private boolean isValidNumber(Object temp) {
        if(temp==null)
            return false;
        if(temp instanceof String) {
            String string=(String)temp;
            if (string.length() == 0)
                return false;
            for(int i=0;i<string.length();i++) {
                if (!Character.isDigit(string.charAt(i))) {
                    return false;
                }
            }
        }
        else
            return false;
        return true;
    }

    public boolean setPracticeAddress( DBObject dbObject, ProviderNPI providerNPI) {
        String[] keys = {
                "Provider First Line Business Practice Location Address" ,
                "Provider Second Line Business Practice Location Address",
                "Provider Business Practice Location Address City Name" ,
                "Provider Business Practice Location Address State Name" ,
                "Provider Business Practice Location Address Postal Code",
                "Provider Business Mailing Address Telephone Number",
                "Provider Business Mailing Address Fax Number"
        };

        if(dbObject==null)
            return false;
        ProviderAddress address = new ProviderAddress(ProviderAddress.PRACTIVE_ADDRESS);
        try {

            buildProviderAddress(dbObject, keys, address);
            providerNPI.getPracticeAddress().add(address);

        }catch (Exception e) {
            //TODO catch content validation exception
            return false ;
        }
        return true ;
    }

    public boolean setProviderName(DBObject dbObject, ProviderNPI providerNPI) {
        String[] keys = {
                "Provider First Name" ,
                "Provider Middle Name" ,
                "Provider Name Prefix Text" ,
                "Provider Name Suffix Text" ,
                "Provider Credential Text"
        };
        if(dbObject==null)
            return false;
        ProviderName providerName = new ProviderName();
        Object obj=  null ;
        try {
            obj = dbObject.get(keys[0]);
            providerName.setFirst((obj instanceof String)?(String)obj:null);
            obj= dbObject.get(keys[1]);
            providerName.setMiddle((obj instanceof String)?(String)obj:null);
            obj = dbObject.get(keys[2]);
            providerName.setPrefix((obj instanceof String)?(String)obj:null);
            obj = dbObject.get(keys[3]);
            providerName.setSuffix((obj instanceof String)?(String)obj:null);
            obj = dbObject.get(keys[4]);
            providerName.setCredential((obj instanceof String)?(String)obj:null);
        }catch (Exception e) {
            //TODO catch content validation exception
            return false ;
        }

        providerNPI.setProviderName(providerName);
        return true ;

    }

    public boolean setOtherProviderName(DBObject dbObject, ProviderNPI providerNPI) {
        String[] keys = {
                "Provider Other Last Name" ,
                "Provider Other First Name" ,
                "Provider Other Middle Name",
                "Provider Other Name Prefix Text",
                "Provider Other Name Suffix Text",
                "Provider Other Credential Text",
                "Provider Other Last Name Type Code"
        };
        ProviderName providerName = new ProviderName();
        Object obj=  null ;
        try {
            obj= dbObject.get(keys[0]);
            providerName.setLast((obj instanceof String)?(String)obj:null);
            obj= dbObject.get(keys[1]);
            providerName.setFirst((obj instanceof String)?(String)obj:null);
            obj =  dbObject.get(keys[2]);
            providerName.setMiddle((obj instanceof String)?(String)obj:null);
            obj =  dbObject.get(keys[3]);
            providerName.setPrefix((obj instanceof String)?(String)obj:null);
            obj = dbObject.get(keys[4]);
            providerName.setSuffix((obj instanceof String)?(String)obj:null);
            obj = dbObject.get(keys[5]);
            providerName.setCredential((obj instanceof String)?(String)obj:null);
            obj = dbObject.get(keys[6]);
            providerName.setLastNameType((obj instanceof String)?(String)obj:null);

        }catch (Exception e) {
            //TODO catch content validation exception
            return false ;
        }

        providerNPI.setAlternateProviderName(providerName);
        return true ;

    }

    public boolean setProviderTaxonomyGroup(DBObject dbObject, ProviderNPI providerNPI) {
        String key ="Healthcare Provider Taxonomy Group";
        Object obj=null;
        for ( int i =1 ; i < 16 ;i++ ) {
            try {

                obj = dbObject.get(key + "_" + i);
                String taxonomyGroup=(obj instanceof String)?(String)obj:null;
                if (taxonomyGroup != null && taxonomyGroup.trim().length() > 0) {
                    providerNPI.getProviderTaxonomyGroup().add(taxonomyGroup);
                }
            }catch (Exception e) {
                return false ;
            }
        }
        return true;
    }

    public boolean setAuthorizedOfficial(DBObject dbObject, ProviderNPI providerNPI) {
        String[] npikeys = {
                "Authorized Official Last Name" ,
                "Authorized Official First Name" ,
                "Authorized Official Middle Name" ,
                "Authorized Official Title or Position" ,
                "Authorized Official Telephone Number" ,
        };

        //TODO load authorizedofficial to providerNPI
        return true ;
    }

    public boolean setOrgInfo(DBObject dbObject, ProviderNPI npi) {
        String[] key = {
                "Employer Identification Number (EIN)" ,
                "Provider Other Organization Name" ,
                "Provider Other Organization Name Type Code" ,
                "Provider Enumeration Date" ,
                "Is Sole Proprietor" ,
                "Is Organization Subpart" ,
                "Parent Organization LBN" ,
                "Parent Organization TIN"
        };
        if(dbObject==null)
            return false;

        try {
            Object obj=dbObject.get(key[0]);
            npi.setProviderEIN((obj instanceof String)?(String)obj:null);
            ProviderOrganization organization = new ProviderOrganization();
            obj=dbObject.get(key[1]);
            organization.setAlternateOrgName((obj instanceof String)?(String)obj:null);
            obj=dbObject.get(key[2]);
            organization.setAlternateOrgTypeCode((obj instanceof String)?(String)obj:null);
            npi.setOrganization(organization);
            obj=dbObject.get(key[3]);
            npi.setProviderEnumerationDate((obj instanceof String)?(String)obj:null);
            obj=dbObject.get(key[4]);
            npi.setIsSoleProprietor((obj instanceof String)?(String)obj:null);
            obj=dbObject.get(key[5]);
            npi.setIsSubPart((obj instanceof String)?(String)obj:null);
            obj=dbObject.get(key[6]);
            npi.setParentOrgLBN((obj instanceof String)?(String)obj:null);
            obj=dbObject.get(key[7]);
            npi.setParentOrgTIN((obj instanceof String)?(String)obj:null);
        } catch ( Exception e){
            //TODO catch content validation exception
            e.printStackTrace();
            return false ;
        }
        return true ;
    }


    public ProviderNPI  getNPIInfo(DBObject dbObject) throws InvalidNPITypeException {
        String[] npikeys = {
                "NPI",
                "Entity Type Code",
                "Replacement NPI" ,
                "Provider Enumeration Date" ,
                "NPI Deactivation Reason Code" ,
                "NPI Deactivation Date" ,
                "NPI Reactivation Date" ,
                "Provider Gender Code",
                "Last Update Date"
        };

        ProviderNPI npi = new ProviderNPI();
        Object obj=dbObject.get(npikeys[0]);
        logger.info("Loading NPI :"+obj.toString());
        if(obj instanceof Integer)npi.setProviderNPI((Integer)obj);
        else if(isValidNumber(obj))npi.setProviderNPI(Integer.parseInt((String)obj));
        else throw new InvalidNPITypeException("Invalid NPI");

        obj = dbObject.get(npikeys[1]);
        if(obj instanceof Integer) {
            int type = (Integer)obj;
            if (type > 0) npi.setProviderNPIEntityType(type);
            else throw new InvalidNPITypeException("Provider Entity type code not valid");
        }
        else if( isValidNumber(obj)) {
            int type = Integer.parseInt((String)obj);
            if (type > 0) npi.setProviderNPIEntityType(type);
            else throw new InvalidNPITypeException("Provider Entity type code not valid");
        }
        else throw new InvalidNPITypeException("Entity Type Code not valid");

        obj = dbObject.get(npikeys[2]);
        if(obj instanceof Integer)npi.setReplacementProviderNPI((Integer)obj);
        else if(isValidNumber(obj))npi.setReplacementProviderNPI(Integer.parseInt((String)obj));
        else npi.setReplacementProviderNPI(0);

        obj = dbObject.get(npikeys[3]);
        npi.setProviderEnumerationDate((obj instanceof String)?(String)obj:null);
        obj = dbObject.get(npikeys[4]);
        npi.setNpiDeactivationReason((obj instanceof String)?(String)obj:null);
        obj = dbObject.get(npikeys[5]);
        npi.setNpiDeactivationDate((obj instanceof String)?(String)obj:null);
        obj = dbObject.get(npikeys[6]);
        npi.setNpiReactivationDate((obj instanceof String)?(String)obj:null);
        obj = dbObject.get(npikeys[7]);
        npi.setGender((obj instanceof String)?(String)obj:null);
        obj = dbObject.get(npikeys[8]);
        npi.setProviderLastUpdateDate((obj instanceof String)?(String)obj:null);

        return npi ;
    }
}
