package com.sidgs.odp.npi.service;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by annap on 10/24/2017.
 */


public class NPIDataLoaderImplTest {
       @Test
       public  void loadEntityProviderTaxonomiesTest(){

           String tx ="akhil";
           int npiId =0;
           DBCursor dbCursor=Mockito.mock(DBCursor.class);
           DBCollection dbCollection =Mockito.mock(DBCollection.class);
           MongoTemplate temple = Mockito.mock(MongoTemplate.class);
           Mockito.when(temple.getCollection("npi-data")).thenReturn(dbCollection);
           NPIDataLoaderImpl npiDataLoader=new NPIDataLoaderImpl();
           npiDataLoader.setMongoTemplate(temple);
           Mockito.when(dbCollection.find()).thenReturn(dbCursor);

           npiDataLoader.loadEntityProviderTaxonomies(tx,npiId);
           Mockito.verify(temple,Mockito.times(1)).getCollection("npi-data");
           Mockito.verify(dbCollection,Mockito.times(1)).find();

       }


}
