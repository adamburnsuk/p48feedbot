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
import com.walmart.qe.mobilebot.exceptions.ProcessNotKilledException;
import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.service.DeviceService;
import com.walmart.qe.mobilebot.service.ReservationService;
import com.walmart.ste.stelabapi.ActiveReservation;
import com.walmart.ste.stelabapi.LabManager;

import se.vidstige.jadb.JadbException;

/**
 * This class is a Rest Controller for devices in the STE Lab tool.  It allows you to do things
 * like add, update, delete devices, but also performs actions on the devices themselves (IE reboot, install apk, etc)
 * 
 * @author a2burns
 *
 */
@RestController
@RequestMapping("/svc/devices")
@Api(value="Mobile Device Management.  Manage devices (Add, Delete, Reboot, Etc).")
public class DeviceController {		
	
		@Autowired
		private DeviceService deviceService;	
		
		/**
		 * Get a list of all devices.  This is a GET REST service.
		 * @return returns a list of type Device   (All devices in the database)
		 * @throws IOException 
		 * @throws JadbException if there is a problem with ADB commands
		 */
	    @RequestMapping(method=RequestMethod.GET, value="", produces = "application/json")
	    public List<Device> getDevices() throws IOException, JadbException{
	    	return this.deviceService.getAllDevices();
	    }
	    
	    /**
	     * This method is used to add a device to the database.  It is a POST REST service.
	     * You must pass a valid Device object to the method.
	     * 
	     * @param device - Device object
	     */
	    @RequestMapping(method=RequestMethod.POST, value="", produces = "application/json")
	    public void addDevice(@RequestBody Device device){
	    	this.deviceService.addDevice(device);
	    }
		
	    /**
	     * This method is used to get a device from the database.  It is a GET REST service.
	     * It returns a device object.
	     * You must pass an ID in your request.  (ID is the serial number of the device)
	     * 
	     * @param id
	     * @return returns a Device object representing the device you asked for
	     * @throws IOException
	     * @throws JadbException
	     */
	    @RequestMapping(method=RequestMethod.GET, value="/{id}", produces = "application/json")
	    public Device getDevice(@PathVariable String id) throws IOException, JadbException {
	    	return this.deviceService.getDevice(id);
	    }
	    
	    /**
	     * This method is used to update device details in the database.  It is a PUT REST service.
	     * You must pass a device in the body and a valid ID (serial number) in the request url.
	     * 
	     * @param device device object representing the updated device details
	     * @param id serial number of the device
	     */
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}", produces = "application/json")
	    public void updateDevice(@RequestBody Device device,@PathVariable String id){
	    	this.deviceService.updateDevice(id, device);
	    }
	    
	    
	    /**
	     * This method is used to delete a device.  It is a DELETE REST service.
	     * You must pass the device ID (serial number) in the request.
	     * 
	     * @param id serial number of the device
	     */
	    @RequestMapping(method=RequestMethod.DELETE, value="/{id}", produces = "application/json")
	    public void deleteDevice(@PathVariable String id){
	    	this.deviceService.deleteDevice(id);
	    }
	    
	    /**
	     * This method is used to get the status of a device.  This is a GET REST service.
	     * You must pass in the device ID (serial number) of the device in the request.
	     * 
	     * @param id serial number of the device
	     * @return Returns a String representing the status of the device. (Ready - ADB Connected and Authorized, Unauthorized - ADB Connected but NOT Authorized, Offline - ADB NOT connected)
	     * @throws IOException
	     * @throws JadbException
	     */
		@GetMapping(value="/{id}/status", produces = "application/json")
		public String getDeviceStatus(@PathVariable String id) throws IOException, JadbException{
			return this.deviceService.getDeviceStatus(id);	
		}
		
		
	    /**
	     * A call to this service will delete all devices in the database. This is a POST REST service.
	     */
	    //@RequestMapping(method=RequestMethod.POST, value="/delete", produces = "application/json")
	    public void deleteDevices() {
	    	this.deviceService.deleteAllDevices();	
	    }
	    
	    /**
	     * This method will reboot a device using ADB commands.  This is a PUT REST service.
	     * 
	     * @param id the serial number of the device
	     * @throws IOException 
	     * @throws JadbException if there is a problem with the ADB command
	     */
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}/reboot", produces = "application/json")
	    public void rebootDevice(@PathVariable String id) throws IOException, JadbException{
	    	this.deviceService.rebootDevice(id);  	
	    }
	    
	    /**
	     * This method will record video on a device.  This is an experimental method and not currently exposed 
	     * as a service. 
	     * 
	     * @param id the serial number of the device you want to record
	     * 
	     * @throws Exception
	     */
	    //@RequestMapping(method=RequestMethod.PUT, value="/{id}/record", produces = "application/json")
	    public void recordDevice(@PathVariable String id) throws Exception{
	    	this.deviceService.recordVideo(id);
	    }
	    
	    /**
	     * A call to this service will attempt to start up an appium session and connect to the device.  This service
	     * is not exposed currently because we want users to have to use their device reservation to perform actions on
	     * a device.  Once security is added, we will expose this service.
	     * 
	     * @param id the serial number of the device
	     * @throws IOException
	     * @throws JadbException
	     * @throws AppiumNotStartedException this exception is thrown if Appium could not be started for some reason.
	     * @throws AppiumNotStoppedException 
	     */
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}/startappium", produces = "application/json")
	    public void startAppiumforDevice(@PathVariable String id) throws IOException, JadbException, AppiumNotStartedException, AppiumNotStoppedException{
	    
	    	//Attempt to start appium...if not started in the specified time, throw exception
	    	if(!this.deviceService.startAppium(id)){
	    		throw new AppiumNotStartedException("Appium could not be started for device.");
	    	}
	    	 
	    }
	    
	    /**
	     * A call to this service will attempt to start up an appium session and connect to the device. The first step is to create a
	     * reservation for a device and then get back the ActiveReservation object.  This is passed to this service in order to verify 
	     * that you do have an active reservation for the device.
	     * 
	     * @param reservation the ActiveReservation object for the device.  
	     * @throws Exception 
	     */
	    //A call to this service will attempt to start up an appium session and connect to the device for a given active reservation
	    @RequestMapping(method=RequestMethod.PUT, value="/startappium", produces = "application/json")
	    public void startAppiumforDevice(@RequestBody ActiveReservation reservation) throws Exception{
	    
	    	//Attempt to start appium...if not started in the specified time, throw exception
	    	if(!this.deviceService.startAppium(reservation)){
	    		throw new AppiumNotStartedException("Appium could not be started for device.");
	    	}
	    	 
	    }
	    
	    
	    /**
	     * A call to this service will attempt to stop an Appium session for a device. You should already have an active reservation
	     * for the device, because you would need this in order to start Appium.   This is passed to this service in order to verify 
	     * that you do have an active reservation for the device.
	     * 
	     * @param reservation the ActiveReservation object for the device
	     * @throws IOException
	     * @throws JadbException
	     * @throws AppiumNotStoppedException this is thrown if Appium could not be stopped for some reason
	     * @throws ProcessNotKilledException 
	     */
	    @RequestMapping(method=RequestMethod.PUT, value="/stopappium", produces = "application/json")
	    public void stopAppiumforDevice(@RequestBody ActiveReservation reservation) throws IOException, JadbException, AppiumNotStoppedException, ProcessNotKilledException{
	    	this.deviceService.stopAppium(reservation);
	    }
	    
	    
	    /**
	     * 
	     * @param id the serial number of the device
	     * @throws IOException
	     * @throws JadbException
	     * @throws AppiumNotStoppedException this is thrown if Appium could not be stopped for some reason
	     * @throws ProcessNotKilledException 
	     */
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}/stopappium", produces = "application/json")
	    public void stopAppiumforDevice(@PathVariable String id) throws IOException, JadbException, AppiumNotStoppedException, ProcessNotKilledException{
	    	this.deviceService.stopAppium(id);
	    }
	    
	    /**
	     * A call to this service will attempt to reserve a device in the mobile lab.  This is a PUT REST service.
	     * 
	     * @param id the id (serial number) of the device.
	     * @return this returns an ActiveReservation object representing the reservation of the device.  
	     * @throws Exception
	     */
	    @RequestMapping(method=RequestMethod.PUT, value="/{id}/reserve", produces = "application/json")
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
