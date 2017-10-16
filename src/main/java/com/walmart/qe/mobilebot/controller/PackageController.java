package com.walmart.qe.mobilebot.controller;

import io.swagger.annotations.Api;

import java.io.IOException;
//Java utility libraries
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

//Spring framework imports
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.qe.mobilebot.model.DeviceFile;
import com.walmart.qe.mobilebot.model.DevicePackage;
import com.walmart.qe.mobilebot.service.DeviceService;

import se.vidstige.jadb.JadbException;


@RestController
@Api(value="Mobile Device Package Management.  Manage apks (Install, Uninstall, Clear Cache).")
public class PackageController {		
	
		@Autowired
		private DeviceService deviceService;
	    
	    //Clear the cache of a particular package on a device
	    @RequestMapping(method=RequestMethod.PUT, value="/svc/devices/{id}/packages/clearcache", produces = "application/json")
	    public void clearPackageCache(@RequestBody DevicePackage myPackage,@PathVariable String id) throws IOException, JadbException{
	    	this.deviceService.clearDevicePackageCache(id, myPackage.getName());
	    }
	    
	    //Install a package from a particular device
	    @RequestMapping(method=RequestMethod.PUT, value="/svc/devices/{id}/packages/install", produces = "application/json")
	    public ResponseEntity<?> installPackage(@PathVariable String id, @RequestBody DeviceFile file) throws IOException, JadbException{
	    	
	    	//Try to install the apk on the device.  If fails, return error
	    	if(this.deviceService.installDevicePackage(id, file.getName().trim()).equalsIgnoreCase("success")){
	    		return ResponseEntity.ok().build();
	    	}
	    	else{
	    		return ResponseEntity.status(HttpStatus.CONFLICT).build();
	    	}
	    	
	    }
	    
	    //Uninstall a package from a particular device
	    @RequestMapping(method=RequestMethod.PUT, value="/svc/devices/{id}/packages/uninstall", produces = "application/json")
	    public void uninstallPackage(@RequestBody DevicePackage myPackage,@PathVariable String id) throws IOException, JadbException{
	    	this.deviceService.uninstallDevicePackage(id, myPackage.getName());
	    }
	    
	    //Get all packages for a device
		@GetMapping(value="/svc/devices/{id}/packages", produces = "application/json")
		public List<DevicePackage> getDevicePackages(@PathVariable String id) throws IOException, JadbException{
			
			return this.deviceService.getPackages(id);
			
		}

}
