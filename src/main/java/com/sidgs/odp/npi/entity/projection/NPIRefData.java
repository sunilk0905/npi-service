package com.sidgs.odp.npi.entity.projection;

import com.sidgs.odp.npi.entity.ProviderNPI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.core.config.Projection;

/**
 * Created on 8/29/2017.
 */
@Projection(name = "npi-ref",  types= {ProviderNPI.class})
public interface NPIRefData {

    @Value("#{target.providerNPI}")
    int getNpi();

    @Value("#{target.providerNPIEntityType}")
    String getNpiType();
    
    @Value("#{target.providerEnumerationDate}")
    String getNPIStartDate();
    
    //TODO: map to last update date
    @Value("#{target.providerLastUpdateDate}")
    String getNPIEndDate();

}
