package com.walmart.qe.mobilebot.device;

//Java utility libraries
import java.util.List;






import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

//Spring framework imports
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DeviceController {		
	
		@Autowired
		private DeviceService deviceService;

	    @RequestMapping("/device/{id}")
	    public Device getDevice(@PathVariable String id) {
	    	return deviceService.getDevice(id);
	    }
	    
	    @RequestMapping(method=RequestMethod.POST, value="/device")
	    public void addDevice(@RequestBody Device device){
	    	deviceService.addDevice(device);
	    }
	    
	    @RequestMapping(method=RequestMethod.PUT, value="/device/{id}")
	    public void updateDevice(@RequestBody Device device,@PathVariable String id){
	    	deviceService.updateDevice(id, device);
	    }
	    
	    @RequestMapping(method=RequestMethod.DELETE, value="/device/{id}")
	    public void deleteDevice(@PathVariable String id){
	    	deviceService.deleteDevice(id);
	    }
	    
	    @RequestMapping("/devices")
	    public List<Device> getDevices(){
	    	return deviceService.getAllDevices();
	    }

}
