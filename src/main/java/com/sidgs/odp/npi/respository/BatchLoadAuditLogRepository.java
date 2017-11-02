package com.sidgs.odp.npi.respository;

import com.sidgs.odp.npi.entity.BatchLoadAuditLog;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path = "batch")

public interface BatchLoadAuditLogRepository extends PagingAndSortingRepository<BatchLoadAuditLog, String> {


    List<BatchLoadAuditLog> findByTransactionID(@Param("tranId") String transactionID);
}
