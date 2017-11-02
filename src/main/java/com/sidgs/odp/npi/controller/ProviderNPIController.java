package com.sidgs.odp.npi.controller;

import com.sidgs.odp.npi.entity.BatchLoadAuditLog;
import com.sidgs.odp.npi.entity.ProviderNPI;
import com.sidgs.odp.npi.entity.projection.NPIRefData;
import com.sidgs.odp.npi.respository.BatchLoadAuditLogRepository;
import com.sidgs.odp.npi.respository.ProviderNPIRepository;
import com.sidgs.odp.npi.service.NPIDataLoader;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.*;

@RestController
@RequestMapping("npi")
@Api(value="providerNPI", description="Load and retrieve ProviderNPI")


public class ProviderNPIController implements BaseController {

    @Autowired
    ProviderNPIRepository repository;

    @Autowired
    BatchLoadAuditLogRepository logRepository ;

    @Autowired
    NPIDataLoader npiDataLoader ;

    boolean isDataBeingRefreshed = false ;


    @ApiOperation(value = "Load ProviderNPI by ID")
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
                List<ProviderNPI> npiList = repository.findByProviderNPI(npi);
                for (ProviderNPI  providerNPI: npiList ) repository.delete(providerNPI.getId());
                // add build provider-identifier from npi-data
                // send the guid so that audit can be built
                npiDataLoader.loadProviderNPI(guid, npi);
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

    @ApiOperation(value = "View a list of available ProviderNPIs",response = ProviderNPI[].class)
    @RequestMapping(method = RequestMethod.GET,
            path = "/id",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<ProviderNPI> getProviderByNpiID(@RequestParam("npi") int npi) {
        List<ProviderNPI>  providerNPIs = repository.findByProviderNPI(npi);
        if ( providerNPIs == null ) return new ArrayList<ProviderNPI>();
        return providerNPIs ;
    }

    @RequestMapping(path = "/reload", method = RequestMethod.POST)
    @ResponseStatus (HttpStatus.OK)
    @ApiOperation(value = "Load multiple ProviderNPIs")
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
                log.setObjectType(ProviderNPI.class.toString());
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
                        npiDataLoader.loadProviderNPI(guid);
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

    @RequestMapping (path = "ref-data",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiOperation(value = "Get NPI reference data of NPI ",response = Map.class)
    public Map<Integer, String> npiRefData() {
        Map<Integer, String> integerMap = new HashMap<Integer, String>();
        for ( ProviderNPI npi : this.repository.findAll()) {
            integerMap.put((Integer)npi.getProviderNPI(),
                    npi.getProviderNPIEntityType() > 1 ? "individual" : "group"  );
        }
        return integerMap;
    }
    
	/**
	 * It fetches NPI reference data with page and limit options
	 * 
	 * @param page
	 * @param
	 * @return
	 */
    @ApiOperation(value = "Get NPI reference data (pagination)",response = NPIRefData[].class)
	@RequestMapping(path = "npi-ref-data",method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<NPIRefData> npiRefData(@RequestParam(value = "page", required = true) int page,
			@RequestParam(value = "limit", required = true) int limit) {

		Sort sort = new Sort(new Order(Direction.ASC, "providerNPI"));
		List<NPIRefData> providerNpiList = this.repository.findNPIRefProjectedBy(new PageRequest(page, limit, sort));
		
		return providerNpiList;
	}
}
