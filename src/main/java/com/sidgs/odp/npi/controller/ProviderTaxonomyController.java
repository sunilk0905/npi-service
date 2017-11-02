package com.sidgs.odp.npi.controller;

import com.sidgs.odp.npi.entity.BatchLoadAuditLog;
import com.sidgs.odp.npi.entity.ProviderNPI;
import com.sidgs.odp.npi.entity.ProviderTaxonomy;
import com.sidgs.odp.npi.respository.BatchLoadAuditLogRepository;
import com.sidgs.odp.npi.respository.ProviderTaxonomyRepository;
import com.sidgs.odp.npi.service.NPIDataLoader;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("taxonomy")
public class ProviderTaxonomyController implements BaseController {

    @Autowired
    ProviderTaxonomyRepository repository;

    @Autowired
    NPIDataLoader npiDataLoader ;

    @Autowired
    ProviderTaxonomy providerTaxonomy;

    @Autowired
    BatchLoadAuditLogRepository logRepository ;

    boolean isDataBeingRefreshed = false ;

    @ApiOperation(value = "returns the status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "successful"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(path = "/reload", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public String reload() {

        if(isDataBeingRefreshed) {
            return "{\"status\" : \"Re-Load in-Progress \"}";
        }

        Runnable taxanomyThread = new Runnable() {
            @Override
            public void run() {
                String guid = UUID.randomUUID().toString();
                BatchLoadAuditLog log = new BatchLoadAuditLog();
                log.setObjectType(ProviderTaxonomy.class.toString());
                log.setTransactionID(guid);

                try {
                    log.setEventType("START_LOAD");
                    logRepository.save(log);

                    logger.info("Starting Reload");
                    if (!isDataBeingRefreshed) {
                        isDataBeingRefreshed = true;
                        repository.deleteAll();
                        npiDataLoader.loadEntityProviderTaxonomies(guid);
                    }
                } finally {
                    isDataBeingRefreshed = false ;
                    // add a new entry
                    log.setId(null);
                    log.setEventDate(new Date());
                    log.setEventType("END_LOAD");
                    logRepository.save(log);
                }
            }
        };
        Thread thread = new Thread(taxanomyThread);
        try {
            thread.start();
        } catch (Exception e) {
            logger.error(e, e);
        }
        return "{\"status\" : \"Load Started\"}";

    }
    @ApiOperation(value = "return taxonomy by id",response = ProviderTaxonomy[].class)
    @RequestMapping(method = RequestMethod.GET,
            path = "/reload/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void load(@PathVariable("id") int npi) {
        // generate a GUID to indicate this actiosn
        String guid = UUID.randomUUID().toString();
        BatchLoadAuditLog log = new BatchLoadAuditLog();
        log.setObjectType(ProviderNPI.class.toString());
        log.setTransactionID(guid);

        try {
            log.setEventType("START_LOAD");
            // start a new batch load
            logRepository.save(log);
            logger.info("Starting Reload");
            if (true) {
                // Clear out the collection
                List<ProviderTaxonomy> npiList = repository.findByNpi(npi);
                for (ProviderTaxonomy provider : npiList) repository.delete(provider.getId());
                // add build provider-identifier from npi-data
                // send the guid so that audit can be built
                npiDataLoader.loadEntityProviderTaxonomies(guid, npi);
            }
        } finally {
            logger.info("Stop/Finish Reload");
            log.setId(null);
            log.setEventType("END_LOAD");
            log.setEventDate(new Date());
            // log a new entry indicating that the batch finished processing
            logRepository.save(log);
        }
    }
}
