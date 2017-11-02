package com.sidgs.odp.npi.respository;

import com.sidgs.odp.npi.entity.ProviderTaxonomy;
import io.swagger.models.auth.In;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "taxonomy")
public interface ProviderTaxonomyRepository extends MongoRepository<ProviderTaxonomy, String> {
    List<ProviderTaxonomy> findByNpi(@Param("id")int npi);
    @Query (value="{'license.number' : ?0}")
    List<ProviderTaxonomy> findByLicenseNumber(@Param("license") String license);
    @Query (value="{'license.state' : ?0}")
    List<ProviderTaxonomy> findByState(@Param("state") String state);

}
