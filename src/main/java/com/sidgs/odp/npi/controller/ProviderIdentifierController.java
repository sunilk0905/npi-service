package com.sidgs.odp.npi.controller;

import com.sidgs.odp.npi.entity.BatchLoadAuditLog;
import com.sidgs.odp.npi.entity.ProviderIdentifier;
import com.sidgs.odp.npi.entity.ProviderNPI;
import com.sidgs.odp.npi.respository.BatchLoadAuditLogRepository;
import com.sidgs.odp.npi.respository.ProviderIdentifierRepository;
import com.sidgs.odp.npi.service.NPIDataLoader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**A Class to
 * Load ProviderIdentifier by Id and Reload ProviderIdentifier
 *
 */

@RestController
@RequestMapping("identifier")
@Api(value="ProviderIdentifier" , description = "Load ProviderIdentifier by Id and Reload ProviderIdentifier")
public class ProviderIdentifierController implements BaseController {

    @Autowired
    ProviderIdentifierRepository repository;

    @Autowired
    BatchLoadAuditLogRepository logRepository ;

    @Autowired
    NPIDataLoader npiDataLoader ;

    boolean isDataBeingRefreshed = false ;

    /**
     * View Status by Reload ProviderIdentifier
     * @return String daata type
     */
    @ApiOperation(value= "View Status by Reload ProviderIdentifier" , response = HttpStatus.class)
    @RequestMapping(path = "/reload", method = RequestMethod.GET)
    @ResponseStatus (HttpStatus.OK)
    public String reload() {

        if( isDataBeingRefreshed ) {
            return "{\"status\" : \"Re-Load in-Progress \"}";
        }

        Runnable myThread = new Runnable() {
            @Override
            public void run() {
                // generate a GUID to indicate this actiosn
                String guid = UUID.randomUUID().toString();
                BatchLoadAuditLog log = new BatchLoadAuditLog();
                log.setObjectType(ProviderIdentifier.class.toString());
                log.setTransactionID(guid);

                try {
                    log.setEventType("START_LOAD");
                    // start a new batch load
                    logRepository.save(log);

                    logger.info("Starting Reload");
                    if (!isDataBeingRefreshed) {
                        isDataBeingRefreshed = true;
                        // Clear out the collection
                        repository.deleteAll();
                        // add build provider-identifier from npi-data
                        // send the guid so that audit can be built
                        npiDataLoader.loadEntityProviderIdentifier(guid);
                    }
                } finally {
                    logger.info ("Stop/Finish Reload");
                    isDataBeingRefreshed = false ;
                    log.setId(null);
                    log.setEventType("END_LOAD");
                    log.setEventDate(new Date());
                    // log a new entry indicating that the batch finished processing
                    logRepository.save(log);
                }
            }
        };
        Thread thread = new Thread(myThread);
        try {
            thread.start();
        } catch (Exception e) {
            logger.error(e, e);
            return "{\"status\" : \"" +e.getMessage()+"\"}";
        }
        return "{\"status\" : \"Load Started\"}";

    }

    /**
     * Load ProviderIdentifier By Id
     * @param npi
     */
    @ApiOperation(value = "Load ProviderIdentifier By Id")
    @RequestMapping(method = RequestMethod.POST,
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
                List<ProviderIdentifier> npiList= repository.findByNpi(npi);
                for (ProviderIdentifier provider : npiList ) repository.delete(provider .getId());
                // add build provider-identifier from npi-data
                // send the guid so that audit can be built
                npiDataLoader.loadEntityProviderIdentifier(guid, npi);


            }

        } finally {
            logger.info ("Stop/Finish Reload");
            log.setId(null);
            log.setEventType("END_LOAD");
            log.setEventDate(new Date());
            // log a new entry indicating that the batch finished processing
            logRepository.save(log);
        }

    }



}
