package com.sidgs.odp.npi.entity;


import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;

@Document (collection = "entity-load-error")
public class EntityLoadError extends BaseEntity {

    @Indexed
    String entityId;

    @Indexed
    Integer npi;

    @Indexed
    String entityType;

    String errorType;
    String errorDescription ;
    String collectionName ;

    @Indexed
    String source ;

    @Indexed
    String transactionId;

    @Indexed
    String appName ;

    Date createdOn = new Date();

    public Date getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Date createdOn) {
        this.createdOn = createdOn;
    }

    public EntityLoadError() {
    }

    public EntityLoadError (String entityId, String collectionName, Exception e) {
        this.entityId = entityId ;
        this.collectionName = entityType ;
        if ( e!= null ) {
            errorType = e.getClass().getCanonicalName();
            StringWriter writer = new StringWriter();
            e.printStackTrace(new PrintWriter(writer));
            errorDescription = e.getMessage()!=null?e.getMessage():writer.toString();
        }
    }

    public EntityLoadError(String entityId, String entityType, String errorType, String errorDescription, String collectionName, Integer npi) {
        this.entityId = entityId;
        this.entityType = entityType;
        this.errorType = errorType;
        this.errorDescription = errorDescription;
        this.collectionName = collectionName;
        this.npi=npi;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Integer getNpi() {
        return npi;
    }

    public void setNpi(Integer npi) {
        this.npi = npi;
    }

    public String getEntityType() {
        return entityType;
    }

    public void setEntityType(String entityType) {
        this.entityType = entityType;
    }

    public String getErrorType() {
        return errorType;
    }

    public void setErrorType(String errorType) {
        this.errorType = errorType;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }
}
