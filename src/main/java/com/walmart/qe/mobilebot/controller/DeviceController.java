package com.walmart.qe.mobilebot.controller;

import java.io.IOException;
//Java utility libraries
import java.util.List;








import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

//Spring framework imports
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.service.DeviceService;

import se.vidstige.jadb.JadbException;


@RestController
public class DeviceController {		
	
		@Autowired
		private DeviceService deviceService;

	    @RequestMapping("/svc/device/{id}")
	    public Device getDevice(@PathVariable String id) {
	    	return deviceService.getDevice(id);
	    }
	    
	    @RequestMapping(method=RequestMethod.POST, value="/svc/device")
	    public void addDevice(@RequestBody Device device){
	    	deviceService.addDevice(device);
	    }
	    
	    @RequestMapping(method=RequestMethod.PUT, value="/svc/device/{id}")
	    public void updateDevice(@RequestBody Device device,@PathVariable String id){
	    	deviceService.updateDevice(id, device);
	    }
	    
	    @RequestMapping(method=RequestMethod.DELETE, value="/svc/device/{id}")
	    public void deleteDevice(@PathVariable String id){
	    	deviceService.deleteDevice(id);
	    }
	    
	    @RequestMapping("/svc/devices")
	    public List<Device> getDevices(){
	    	return deviceService.getAllDevices();
	    }
	    
	    //A call to this service will immediately reboot the phone
	    @RequestMapping("/svc/device/reboot")
	    public void rebootDevice() throws IOException, JadbException{
	    	deviceService.rebootDevice("1");  	
	    }
	    
	    //Record video on device
	    @RequestMapping("/svc/device/record")
	    public void recordDevice() throws Exception{
	    	deviceService.recordVideo("1");
	    }

}
