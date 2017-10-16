package com.walmart.qe.mobilebot.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.walmart.qe.mobilebot.model.Device;

public interface DeviceRepository extends MongoRepository<Device, String> {

	public Device findBySerial(String serial);
	
}
