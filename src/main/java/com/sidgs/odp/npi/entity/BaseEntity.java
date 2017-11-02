package com.sidgs.odp.npi.entity;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;

public class BaseEntity {

    @Id
   String id ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
