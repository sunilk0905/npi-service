package com.sidgs.odp.npi.service;


import com.sidgs.odp.npi.entity.EntityLoadError;
import com.sidgs.odp.npi.entity.ProviderIdentifier;
import com.sidgs.odp.npi.entity.ProviderNPI;
import com.sidgs.odp.npi.entity.ProviderTaxonomy;
import com.sidgs.odp.npi.error.InvalidNPITypeException;
import com.sidgs.odp.npi.respository.EntityLoadErrorRepository;
import com.sidgs.odp.npi.respository.ProviderIdentifierRepository;
import com.sidgs.odp.npi.respository.ProviderNPIRepository;
import com.sidgs.odp.npi.respository.ProviderTaxonomyRepository;
import com.sidgs.odp.npi.util.NPIDataLoaderUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class NPIDataLoaderImpl implements NPIDataLoader {



    @Autowired
    MongoTemplate mongoTemplate ;

    public MongoTemplate getMongoTemplate() {
        return mongoTemplate;
    }

    public void setMongoTemplate(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Autowired

    ProviderTaxonomyRepository taxonomyRepository ;

    @Autowired
    ProviderIdentifierRepository identifierRepository;

    @Autowired
    ProviderNPIRepository npiRepository;

    @Autowired
    EntityLoadErrorRepository errorRepository ;

    public void loadEntityProviderTaxonomies(String tx, int npiId) {
        List<ProviderTaxonomy> providerTaxonomies = new ArrayList<ProviderTaxonomy>();
        logger.info("Fetch ");
        int batchCount =0 ;
        DBCollection collection =   mongoTemplate.getCollection("npi-data");

        DBCursor dbCursor ;
        if ( npiId > 0 ) {
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("NPI", npiId);
            dbCursor = collection.find(whereQuery);
        }
        else dbCursor = collection.find();


        while ( dbCursor.hasNext() ) {
            DBObject dbObject  = dbCursor.next();
            int npi = (int) dbObject.get("NPI");
            ObjectId jsonID = (ObjectId) dbObject.get("_id");

            try {
            int entityCodeType  =  (int) dbObject.get("Entity Type Code");
            logger.info("Loading NPI: " + npi);
            for ( int i = 1 ; i < 16 ; i++ ) {
                if ( dbObject.containsField(Healthcare_Provider_Taxonomy_Code+i)){
                    String code = String.valueOf(dbObject.get(Healthcare_Provider_Taxonomy_Code+i));
                    // only add when the taxomony exists
                    if( code.trim().length()> 0 ) {
                        String licnum = String.valueOf(dbObject.get(Provider_License_Number + i));
                        String state = String.valueOf(dbObject.get(Provider_License_Number_State_Code + i));
                        String primary = String.valueOf(dbObject.get(Healthcare_Provider_Primary_Taxonomy_Switch + i));
                        ProviderTaxonomy providerTaxonomy = new ProviderTaxonomy(npi, entityCodeType, code);
                        providerTaxonomy.setLicense(licnum, state);
                        taxonomyRepository.save(providerTaxonomy);
                        batchCount++;
                        if( batchCount > 10000 ) {
                            logger.info("Stopping for 10 Seconds before resuming load of 10k taxonomies");
                            Thread.sleep(10*1000);
                            batchCount = 0 ;
                        }
                    }
                }
            }
        } catch (Exception e) {
                logger.warn("Could not load the: " + npi);
                errorRepository.save(getEntityLoadError(jsonID.toString(), "npi-data", e, tx, ProviderTaxonomy.class));
            }
        }
    }


    @Override
    public void loadProviderNPI(String tx, int npiId)  {
        DBCollection collection =  mongoTemplate.getCollection("npi-data");
        DBCursor dbCursor ;
        BasicDBObject whereQuery = new BasicDBObject();
        if ( npiId > 0 ) {
            whereQuery.put("NPI", npiId);
        }
        dbCursor = collection.find(whereQuery);
        errorRepository.deleteAll();
        int batchCount = 0;
        while ( dbCursor.hasNext() ) {
            DBObject dbObject  = dbCursor.next();
            ObjectId jsonID = (ObjectId) dbObject.get("_id");
            NPIDataLoaderUtil util = new NPIDataLoaderUtil();
            try {
                ProviderNPI npiObject = util.getNPIInfo(dbObject);

                if ( ! util.setAuthorizedOfficial(dbObject, npiObject)){
                    errorRepository.save(getEntityLoadErrorUsingErrorMessage(jsonID.toString(), "npi-data",
                            "Error Setting Authorized Official", tx, ProviderNPI.class,npiObject.getProviderNPI()));
                }

                if ( ! util.setBusinessAddress(dbObject, npiObject)){
                    logger.error("error loading Business address for npi:"+npiObject.getProviderNPI());
                    errorRepository.save(getEntityLoadErrorUsingErrorMessage(jsonID.toString(), "npi-data",
                            "Error Setting Biz Address", tx, ProviderNPI.class,npiObject.getProviderNPI()));
                }

                if ( ! util.setOrgInfo(dbObject, npiObject)){
                    logger.error("error loading organization info for NPI:"+ npiObject.getProviderNPI());
                    errorRepository.save(getEntityLoadErrorUsingErrorMessage(jsonID.toString(), "npi-data",
                            "Error Setting Organization Info", tx, ProviderNPI.class,npiObject.getProviderNPI()));
                }

                if ( ! util.setPracticeAddress(dbObject, npiObject)){
                    logger.error("error loading Practice address for npi:"+npiObject.getProviderNPI());
                    errorRepository.save(getEntityLoadErrorUsingErrorMessage(jsonID.toString(), "npi-data",
                            "Error Setting Practice Address ", tx, ProviderNPI.class,npiObject.getProviderNPI()));
                }

                if ( ! util.setProviderName(dbObject, npiObject)){
                    logger.error("error loading Provider name for npi:"+npiObject.getProviderNPI());
                    errorRepository.save(getEntityLoadErrorUsingErrorMessage(jsonID.toString(), "npi-data",
                            "Error Setting Provider Name", tx, ProviderNPI.class,npiObject.getProviderNPI()));
                }

                if ( ! util.setOtherProviderName(dbObject, npiObject)){
                    logger.error("error loading Provider Name for npi:"+npiObject.getProviderNPI());
                    errorRepository.save(getEntityLoadErrorUsingErrorMessage(jsonID.toString(), "npi-data",
                            "Error Setting Other Provider Name", tx, ProviderNPI.class,npiObject.getProviderNPI()));
                }

                if ( ! util.setProviderTaxonomyGroup(dbObject, npiObject)){
                    logger.error("error loading Taxonomy Groupfor npi:"+npiObject.getProviderNPI());
                    errorRepository.save(getEntityLoadErrorUsingErrorMessage(jsonID.toString(), "npi-data",
                            "Error Setting Provider Taxonomy Group", tx, ProviderNPI.class,npiObject.getProviderNPI()));
                }
                logger.debug("Adding Object " + npiObject.getProviderNPI() + " to collection provider-npi");
                if(  batchCount > 10000 ) {
                    try {
                        Thread.sleep(10*1000);
                    } catch (InterruptedException e) {
                    }
                    batchCount=0;
                }
                npiRepository.save(npiObject);
                logger.info("loaded NPI:"+npiObject.getProviderNPI());
                batchCount++ ;
                logger.debug("Added Object {_id:" + npiObject.getId()  +
                        ", NPI: " + npiObject.getProviderNPI() + " } to collection provider-npi"  );


            } catch (InvalidNPITypeException e) {
                logger.error("Could not load the document with Id: " + jsonID.toString());
                errorRepository.save(getEntityLoadError(jsonID.toString(), "npi-data", e, tx, ProviderNPI.class));
            }
        }
    }

    public void loadEntityProviderIdentifier(String tx, int npiId) {
        DBCollection collection =  mongoTemplate.getCollection("npi-data");
        DBCursor dbCursor ;
        if ( npiId > 0 ) {
            BasicDBObject whereQuery = new BasicDBObject();
            whereQuery.put("NPI", npiId);
            dbCursor = collection.find(whereQuery);
        }
        else dbCursor = collection.find();

        int batchCount =0;
        while ( dbCursor.hasNext() ) {
            DBObject dbObject  = dbCursor.next();
            int npi = (int) dbObject.get("NPI");
            ObjectId jsonID = (ObjectId) dbObject.get("_id");
            try {
            int entityCodeType  = (int) dbObject.get("Entity Type Code");
            for ( int i = 1 ; i < 16 ; i++ ) {
                if ( dbObject.containsField(OtherProviderIdentifier +i)){
                    ProviderIdentifier identifier = new ProviderIdentifier();
                    identifier.setNpi(npi);

                        identifier.setNpiType(entityCodeType);
                        String otherProvId = String.valueOf(dbObject.get(OtherProviderIdentifier + i));
                        if (otherProvId != null && otherProvId.trim().length() > 0) {
                            identifier.setId(String.valueOf(dbObject.get(OtherProviderIdentifier + i)));
                            identifier.setProvIdentifierCode(String.valueOf(dbObject.get(OtherProviderIdentifierTypeCode + i)));
                            identifier.setProvIdentifierState(String.valueOf(dbObject.get(OtherProviderIdentifierState + i)));
                            identifier.setProvIdentifierIssuedBy(String.valueOf(dbObject.get(OtherProviderIdentifierIssuer + i)));
                            logger.info("adding Data : " + identifier.toString());
                            identifierRepository.save(identifier);
                            batchCount++;
                            if( batchCount > 10000 ) {
                                logger.info("Stopping for 10 Seconds before resuming load of 10k identifiers");
                                Thread.sleep(10*1000);
                                batchCount = 0 ;
                            }
                        }
                }
            }
            }catch ( Exception e) {
                logger.warn("Error with NPI " + npi);
                errorRepository.save(getEntityLoadError(jsonID.toString(), "npi-data", e, tx, ProviderIdentifier.class));
            }
        }
    }

    protected EntityLoadError getEntityLoadError (String id, String collection, Exception e, String tx, Class entityType){
        EntityLoadError  error = new EntityLoadError(id, "npi-data", e);
        error.setEntityType(entityType.getName());
        error.setAppName(this.getClass().getName());
        error.setTransactionId(tx);
        error.setCollectionName(collection);
        error.setSource(this.getClass().getSimpleName());
        return error ;
    }


    protected EntityLoadError getEntityLoadErrorUsingErrorMessage (String id, String collection, String errorMessage, String tx, Class entityType, Integer npi){
        EntityLoadError  error = new EntityLoadError(id,null,null,null,collection, npi);
        error.setErrorDescription(errorMessage);
        error.setEntityType(entityType.getName());
        error.setAppName(this.getClass().getName());
        error.setTransactionId(tx);
        error.setCollectionName(collection);
        error.setSource(this.getClass().getSimpleName());
        return error ;
    }
        @Override
    public void loadEntityProviderTaxonomies(String transactionId) {
        this.loadEntityProviderTaxonomies(transactionId, 0);
    }

    @Override
    public void loadEntityProviderIdentifier(String transactionId) {
        this.loadEntityProviderIdentifier(transactionId, 0);
    }

    @Override
    public void loadProviderNPI(String transactionId) {
        this.loadProviderNPI(transactionId, 0);
    }
}
