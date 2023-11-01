package com.apmm.dao;

import com.apmm.domain.Location;
import com.apmm.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class LocationDao {
	
	/*@Autowired
	LocationRepository locationrepository;
	
	public void createLocationItems(Location vd) {
		System.out.println("Data creation started...");
		locationrepository.save(vd);
		System.out.println("Data creation complete...");
	}*/
	
	/*public Mono<Location> getLocationItemById(String id) {
		System.out.println("Getting item by name: " + id);
		return locationrepository.findById(id);
	}
	public Flux<Location> getAllLocation() {
		return locationrepository.findAll();
	}*/

}
