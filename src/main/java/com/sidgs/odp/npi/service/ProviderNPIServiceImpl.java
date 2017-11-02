package com.sidgs.odp.npi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Map;

/**
 * Created 8/29/2017
 */
public class ProviderNPIServiceImpl implements ProviderNPIService{

    @Autowired
    MongoTemplate mongoTemplate;

    @Override
    public Map<String, Object> exportNPIRefData() {
        return null;
    }
}
