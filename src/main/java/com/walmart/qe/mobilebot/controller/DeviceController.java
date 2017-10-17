package com.walmart.qe.mobilebot.controller;

import io.swagger.annotations.Api;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

//Spring framework imports
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.qe.mobilebot.exceptions.AppiumNotStartedException;
import com.walmart.qe.mobilebot.exceptions.AppiumNotStoppedException;
import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.service.DeviceService;
import com.walmart.qe.mobilebot.service.ReservationService;
import com.walmart.ste.stelabapi.ActiveReservation;
import com.walmart.ste.stelabapi.LabManager;
import com.walmart.ste.stelabapi.NoAvailableDeviceException;

import se.vidstige.jadb.JadbException;

@RestController
@RequestMapping("/svc/devices")
@Api(value="Mobile Device Management.  Manage devices (Add, Delete, Reboot, Etc).")
public class DeviceController {		
	
		@Autowired
		private DeviceService deviceService;
		
		//Get a list of all devices
	    @RequestMapping(method=RequestMethod.GET, value="", produces = "application/json")
	    public List<Device> getDevices() throws IOException, JadbException{
	    	return this.deviceService.getAllDevices();
	    }
	    
		//Create device
	    @RequestMapping(method=RequestMethod.POST, value="", produces = "application/json")
	    public void addDevice(@RequestBody Device device){
	    	this.deviceService.addDevice(device);
	    }
		
		//Read device by ID
	    @RequestMapping(method=RequestMethod.GET, value="/{id}", produces = "application/json")
	    public Device getDevice(@PathVariable String id) throws IOException, JadbException {
	    	return this.deviceService.getDevice(id);
	    }
	    
	    //Update a device by id
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}", produces = "application/json")
	    public void updateDevice(@RequestBody Device device,@PathVariable String id){
	    	this.deviceService.updateDevice(id, device);
	    }
	    
	    //Delete a device by ID
	    @RequestMapping(method=RequestMethod.DELETE, value="/{id}", produces = "application/json")
	    public void deleteDevice(@PathVariable String id){
	    	this.deviceService.deleteDevice(id);
	    }
	    
	    //Get status of a device by device ID
		@GetMapping(value="/{id}/status", produces = "application/json")
		public String getDeviceStatus(@PathVariable String id) throws IOException, JadbException{
			return this.deviceService.getDeviceStatus(id);	
		}
		
	    //A call to this service will delete all devices in database
	    @RequestMapping(method=RequestMethod.POST, value="/delete", produces = "application/json")
	    public void deleteDevices() {
	    	this.deviceService.deleteAllDevices();	
	    }
	    
	    //A call to this service will immediately reboot the phone
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}/reboot", produces = "application/json")
	    public void rebootDevice(@PathVariable String id) throws IOException, JadbException{
	    	this.deviceService.rebootDevice(id);  	
	    }
	    
	    //Record video on device
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}/record", produces = "application/json")
	    public void recordDevice(@PathVariable String id) throws Exception{
	    	this.deviceService.recordVideo(id);
	    }
	    
	    //A call to this service will attempt to start up an appium session and connect to the device
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}/startappium", produces = "application/json")
	    public void startAppiumforDevice(@PathVariable String id) throws IOException, JadbException, AppiumNotStartedException{
	    
	    	//Attempt to start appium...if not started in the specified time, throw exception
	    	if(!this.deviceService.startAppium(id)){
	    		throw new AppiumNotStartedException("Appium could not be started for device.");
	    	}
	    	 
	    }
	    
	    //A call to this service will attempt to start up an appium session and connect to the device for a given active reservation
	    @RequestMapping(method=RequestMethod.PUT, value="/startappium", produces = "application/json")
	    public void startAppiumforDevice(@RequestBody ActiveReservation reservation) throws IOException, JadbException, AppiumNotStartedException, NoAvailableDeviceException{
	    
	    	//Attempt to start appium...if not started in the specified time, throw exception
	    	if(!this.deviceService.startAppium(reservation)){
	    		throw new AppiumNotStartedException("Appium could not be started for device.");
	    	}
	    	 
	    }
	    
	    //A call to this service will attempt to start up an appium session and connect to the device
	    @RequestMapping(method=RequestMethod.PUT, value="/stopappium", produces = "application/json")
	    public void stopAppiumforDevice(@RequestBody ActiveReservation reservation) throws IOException, JadbException, AppiumNotStoppedException{
	    	this.deviceService.stopAppium(reservation);
	    }
	    
	    //A call to this service will attempt to start up an appium session and connect to the device
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}/stopappium", produces = "application/json")
	    public void stopAppiumforDevice(@PathVariable String id) throws IOException, JadbException, AppiumNotStoppedException{
	    	this.deviceService.stopAppium(id);
	    }
	    
	    //A call to this service will attempt to reserve a device in the mobile lab
	    //@RequestMapping(method=RequestMethod.PUT, value="/{id}/reserve", produces = "application/json")
	    public ActiveReservation reserveDevice(@PathVariable String id) throws Exception{
	    	
	    	LabManager sf = new LabManager();
			ReservationService rs = new ReservationService();
			ActiveReservation ar = new ActiveReservation();
			Device device = deviceService.getDevice(id);
			
			sf.Login("admin", "admin");
					
			ar = rs.createReservation(sf, device, 900);

			//If reservation is successful, start up appium for device
			if(ar!=null){
				startAppiumforDevice(device.getSerial());
			}
			
			return ar;
			
	    }

}
