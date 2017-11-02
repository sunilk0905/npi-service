package com.sidgs.odp.npi.respository;

import com.sidgs.odp.npi.entity.ProviderIdentifier;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource (path = "identifier")
public interface ProviderIdentifierRepository extends PagingAndSortingRepository<ProviderIdentifier, String> {

    List<ProviderIdentifier> findByNpi(@Param("npi")int id);

}
