//package com.sidgs.odp.npi;
//
//import com.sidgs.odp.npi.entity.ProviderNPI;
//import com.sidgs.odp.npi.respository.ProviderNPIRepository;
//import com.sidgs.odp.npi.service.NPIDataLoader;
//import com.mongodb.DBObject;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.util.UUID;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class NPIDataLoaderTest {
//
//	@Autowired
//	NPIDataLoader npiDataLoader;
//
//	@Autowired
//	MongoTemplate mongoTemplate;
//
//	@Autowired
//	ProviderNPIRepository repository;
//
//	@Test
//	public void npiLoad() {
//		DBObject object = mongoTemplate.getCollection("npi-data").findOne();
//		int npi = (int) object.get("NPI");
//
//		for ( ProviderNPI npi1: repository.findByProviderNPI(npi)){
//			repository.delete(npi1.getId());
//		}
//
//		System.out.println(object.get("Replacement NPI"));
//		npiDataLoader.loadProviderNPI(UUID.randomUUID().toString(), npi );
//
//	}
//
//
//
//}
