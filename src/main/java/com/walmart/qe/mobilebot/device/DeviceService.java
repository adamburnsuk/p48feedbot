package com.walmart.qe.mobilebot.device;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	public List<Device> getAllDevices(){
		
		//Create arraylist to hold the list of devices returned by the database
		List<Device> devices = new ArrayList<>();
		
		//Get all devices from the database and convert to device List using lamda expression (java8)
		deviceRepository.findAll().forEach(devices::add);
		
		//Return the list of devices
		return devices;
	}
	
	public Device getDevice(String id){

			//Find the device by ID using the findOne method of DeviceRepository, and return it
			return deviceRepository.findOne(id);
			
	}

	public void addDevice(Device device) {
		
		//Save the new device to the database using the DeviceRepository save method.
		deviceRepository.save(device);
		
	}

	public void updateDevice(String id, Device device) {
		
		//Save the updated device to the database using the DeviceRepository save method.
		deviceRepository.save(device);
		
	}

	public void deleteDevice(String id) {

		deviceRepository.delete(id);
		
	}
}
