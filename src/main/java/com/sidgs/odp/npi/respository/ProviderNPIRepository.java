package com.sidgs.odp.npi.respository;

import com.sidgs.odp.npi.entity.ProviderNPI;
import com.sidgs.odp.npi.entity.projection.NPIRefData;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "npi")

public interface ProviderNPIRepository extends MongoRepository<ProviderNPI, String> {

    List<ProviderNPI> findByProviderNPI(@Param("npi")int npi);

    List<NPIRefData> findNPIRefProjectedBy(Pageable pageable);
}
