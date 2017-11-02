package com.sidgs.odp.npi.respository;

import com.sidgs.odp.npi.entity.EntityLoadError;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "error")
public interface EntityLoadErrorRepository extends PagingAndSortingRepository<EntityLoadError, String> {
}
