package com.walmart.qe.mobilebot.device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;

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
	
	//This method is used to capture a screen recording to the device
	public void recordVideo(String deviceID) throws Exception {
		JadbConnection jadb = new JadbConnection();
    	List<JadbDevice> devices = jadb.getDevices();
    	
    	devices.get(0).executeShell("screenrecord", "/sdcard/screenshots/video_1.mp4 --timelimit 10");
	}
	
	//Reboot android device by ID (This is the ID stored in the database)
	public void rebootDevice(String id) throws IOException, JadbException {
		JadbConnection jadb = new JadbConnection();
    	List<JadbDevice> devices = jadb.getDevices();
    	
    	devices.get(0).executeShell("reboot", "");	  
	}
}
