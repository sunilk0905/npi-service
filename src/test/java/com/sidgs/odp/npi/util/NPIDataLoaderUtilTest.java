package com.sidgs.odp.npi.util;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.sidgs.odp.npi.entity.ProviderNPI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by SUNIL PC on 25-10-2017.
 */
public class NPIDataLoaderUtilTest {

    ProviderNPI providerNPI;
    NPIDataLoaderUtil npiDataLoaderUtil;
    DBObject dbObject;
    @Before
    public void setup(){
        providerNPI=new ProviderNPI();
        npiDataLoaderUtil=new NPIDataLoaderUtil();
        dbObject=new BasicDBObject();
    }

    @Test
    public void setBusinessAddressWithNotNullDBObject(){

        dbObject.put("Provider First Line Business Mailing Address","63 Atherton Drive");
        dbObject.put("Provider Second Line Business Mailing Address","");
        dbObject.put("Provider Business Mailing Address City Name","Exton");
        dbObject.put("Provider Business Mailing Address State Name","PA");
        dbObject.put("Provider Business Mailing Address Postal Code","19341");
        dbObject.put("Provider Business Mailing Address Telephone Number",(long)2038902356);
        dbObject.put("Provider Business Mailing Address Fax Number","12234567");
        boolean result=npiDataLoaderUtil.setBusinessAddress(dbObject,providerNPI);
        Assert.assertEquals((String)dbObject.get("Provider First Line Business Mailing Address"),providerNPI.getBusinessAddress().get(0).getLine1());
        Assert.assertEquals((String)dbObject.get("Provider Second Line Business Mailing Address"),providerNPI.getBusinessAddress().get(0).getLine2());
        Assert.assertEquals((String)dbObject.get("Provider Business Mailing Address City Name"),providerNPI.getBusinessAddress().get(0).getCity());
        Assert.assertEquals((String)dbObject.get("Provider Business Mailing Address State Name"),providerNPI.getBusinessAddress().get(0).getState());
        Assert.assertEquals((String)dbObject.get("Provider Business Mailing Address Postal Code"),Integer.toString(providerNPI.getBusinessAddress().get(0).getPostalCode()));
        Assert.assertEquals(dbObject.get("Provider Business Mailing Address Telephone Number"),providerNPI.getBusinessAddress().get(0).getTeleNumber());
        Assert.assertEquals((String)dbObject.get("Provider Business Mailing Address Fax Number"),Long.toString(providerNPI.getBusinessAddress().get(0).getFaxNumber()));

        Assert.assertEquals(true,result);

    }

    @Test
    public void setBusinessAddressNegativetest(){

        boolean result=npiDataLoaderUtil.setBusinessAddress(null,new ProviderNPI());
        Assert.assertEquals(false,result);

    }

    @Test
    public void setPracticeAddressPositiveTest(){

        dbObject.put("Provider First Line Business Practice Location Address","63 Atherton Drive");
        dbObject.put("Provider Second Line Business Practice Location Address","");
        dbObject.put("Provider Business Practice Location Address City Name","Exton");
        dbObject.put("Provider Business Practice Location Address State Name","PA");
        dbObject.put("Provider Business Practice Location Address Postal Code","19341");
        dbObject.put("Provider Business Mailing Address Telephone Number",(long)2038902356);
        dbObject.put("Provider Business Mailing Address Fax Number","12234567");
        boolean result=npiDataLoaderUtil.setPracticeAddress(dbObject,providerNPI);
        Assert.assertEquals((String)dbObject.get("Provider First Line Business Practice Location Address"),providerNPI.getPracticeAddress().get(0).getLine1());
        Assert.assertEquals((String)dbObject.get("Provider Second Line Business Practice Location Address"),providerNPI.getPracticeAddress().get(0).getLine2());
        Assert.assertEquals((String)dbObject.get("Provider Business Practice Location Address City Name"),providerNPI.getPracticeAddress().get(0).getCity());
        Assert.assertEquals((String)dbObject.get("Provider Business Practice Location Address State Name"),providerNPI.getPracticeAddress().get(0).getState());
        Assert.assertEquals((String)dbObject.get("Provider Business Practice Location Address Postal Code"),Integer.toString(providerNPI.getPracticeAddress().get(0).getPostalCode()));
        Assert.assertEquals(dbObject.get("Provider Business Mailing Address Telephone Number"),providerNPI.getPracticeAddress().get(0).getTeleNumber());
        Assert.assertEquals((String)dbObject.get("Provider Business Mailing Address Fax Number"),Long.toString(providerNPI.getPracticeAddress().get(0).getFaxNumber()));

        Assert.assertEquals(true,result);
    }
    @Test
    public void setPracticeAddressNegativetest(){

        boolean result=npiDataLoaderUtil.setPracticeAddress(null,new ProviderNPI());
        Assert.assertEquals(false,result);

    }
    @Test
    public  void setProviderNameNegativeTest(){
        boolean result = npiDataLoaderUtil.setProviderName(null,providerNPI);
        Assert.assertEquals(false,result);
    }

    @Test
    public void setProviderNamePositiveTest(){
        dbObject.put("Provider First Name","Dhoni");
        dbObject.put("Provider Middle Name","MS");
        dbObject.put("Provider Name Prefix Text","MR");
        dbObject.put("Provider Name Suffix Text","");
        dbObject.put("Provider Credential Text","");
        boolean result = npiDataLoaderUtil.setProviderName(dbObject,providerNPI);
        Assert.assertEquals((String)dbObject.get("Provider First Name"),providerNPI.getProviderName().getFirst());
        Assert.assertEquals((String)dbObject.get("Provider Middle Name"),providerNPI.getProviderName().getMiddle());
        Assert.assertEquals((String)dbObject.get("Provider Name Prefix Text"),providerNPI.getProviderName().getPrefix());
        Assert.assertEquals((String)dbObject.get("Provider Name Suffix Text"),providerNPI.getProviderName().getSuffix());
        Assert.assertEquals((String)dbObject.get("Provider Credential Text"),providerNPI.getProviderName().getCredential());
        Assert.assertEquals(true,result);

    }


    @Test
    public void setOtherProviderNamenegativeTest(){
        boolean result = npiDataLoaderUtil.setOtherProviderName(null,providerNPI);
        Assert.assertEquals(false,result);
    }


    @Test
    public void setOtherProviderNamePositiveTest(){
        dbObject.put("Provider Other Last Name" ,"MS");
        dbObject.put("Provider Other First Name"  ,"Dhoni");
        dbObject.put("Provider Other Middle Name","");
        dbObject.put( "Provider Other Name Prefix Text","Mr");
        dbObject.put( "Provider Other Name Suffix Text","");
        dbObject.put( "Provider Other Credential Text","Cricketer");
        dbObject.put("Provider Other Last Name Type Code","captian");
        boolean result = npiDataLoaderUtil.setOtherProviderName(dbObject,providerNPI);
        Assert.assertEquals((String)dbObject.get("Provider Other Last Name"),providerNPI.getAlternateProviderName().getLast());
        Assert.assertEquals((String)dbObject.get("Provider Other First Name"),providerNPI.getAlternateProviderName().getFirst());
        Assert.assertEquals((String)dbObject.get("Provider Other Middle Name"),providerNPI.getAlternateProviderName().getMiddle());
        Assert.assertEquals((String)dbObject.get("Provider Other Name Prefix Text"),providerNPI.getAlternateProviderName().getPrefix());
        Assert.assertEquals((String)dbObject.get("Provider Other Name Suffix Text"),providerNPI.getAlternateProviderName().getSuffix());
        Assert.assertEquals((String)dbObject.get("Provider Other Credential Text"),providerNPI.getAlternateProviderName().getCredential());
        Assert.assertEquals((String)dbObject.get("Provider Other Last Name Type Code"),providerNPI.getAlternateProviderName().getLastNameType());
        Assert.assertEquals(true,result);
    }



    @Test
    public void setProviderTaxonomyGroupNullDbobjectTest()
    {
        boolean res=npiDataLoaderUtil.setProviderTaxonomyGroup(null,providerNPI);
        Assert.assertEquals(false,res);
    }

    @Test
    public void setProviderTaxonomyGroupTest(){
        dbObject.put("Healthcare Provider Taxonomy Group","group");
        boolean res= npiDataLoaderUtil.setProviderTaxonomyGroup(dbObject,providerNPI);
        Assert.assertEquals(dbObject.get("Healthcare Provider Taxonomy Group"),providerNPI.getProviderTaxonomyGroup().get(0));
    }

    @Test
    public void setOrgInfoNullDbObjectTest(){
        boolean res=npiDataLoaderUtil.setOrgInfo(null,providerNPI);
        Assert.assertEquals(false,res);
    }

    @Test
    public void setOrgInfoTest(){
        dbObject.put("Employer Identification Number (EIN)","1234");
        dbObject.put("Provider Other Organization Name","sidgs");
        dbObject.put("Provider Other Organization Name Type Code","qwerty");
        dbObject.put("Provider Enumeration Date","10/26/2017");
        dbObject.put("Is Sole Proprietor","NO");
        dbObject.put("Is Organization Subpart","YES");
        dbObject.put("Parent Organization LBN","123EE");
        dbObject.put("Parent Organization TIN","123R4");
        boolean res=npiDataLoaderUtil.setOrgInfo(dbObject,providerNPI);

        Assert.assertEquals(dbObject.get("Employer Identification Number (EIN)"),providerNPI.getProviderEIN());
//        Assert.assertEquals(dbObject.get("Provider Other Organization Name)"),providerNPI.getAlternateProviderName());
//        Assert.assertEquals(dbObject.get("Provider Other Organization Name Type Code"),providerNPI.getAlternateProviderName());
        Assert.assertEquals(dbObject.get("Provider Enumeration Date"),providerNPI.getProviderEnumerationDate());
        Assert.assertEquals(dbObject.get("Is Sole Proprietor"),providerNPI.getIsSoleProprietor());
        Assert.assertEquals(dbObject.get("Is Organization Subpart"),providerNPI.getIsSubPart());
        Assert.assertEquals(dbObject.get("Parent Organization LBN"),providerNPI.getParentOrgLBN());
        Assert.assertEquals(dbObject.get("Parent Organization TIN"),providerNPI.getParentOrgTIN());
    }
}