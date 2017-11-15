package com.walmart.qe.mobilebot.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import se.vidstige.jadb.JadbException;

import com.walmart.qe.mobilebot.data.ReservationRepository;
import com.walmart.qe.mobilebot.exceptions.AppiumNotStoppedException;
import com.walmart.qe.mobilebot.exceptions.ProcessNotKilledException;
import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.model.DeviceFile;
import com.walmart.qe.mobilebot.model.Reservation;
import com.walmart.qe.mobilebot.service.DeviceService;
import com.walmart.qe.mobilebot.service.ReservationService;
import com.walmart.qe.mobilebot.service.StorageService;
import com.walmart.ste.stelabapi.ActiveReservation;
import com.walmart.ste.stelabapi.LabManager;

@Controller
public class ViewController {

	@Autowired
	private DeviceService deviceService;
	
	@Autowired
	private StorageService storageService;
	
	@Autowired
	private ReservationRepository resRepository;
	
	@RequestMapping("/")
	public String index(Model model){
		
		model.addAttribute("datetime", new Date());
		
		return "index";
		
	}
	
	//Get all devices
	@GetMapping(value="/devices")
	public String getDevice(Model model){
		
		//Get list of filenames
		List<String> fileNames=storageService.getFileNames("C:\\toolbox\\Mobile_Bot\\mobile-bot\\src\\main\\resources\\static\\images\\devices");

        
		model.addAttribute("device", new Device());
		model.addAttribute("imageFiles", fileNames);
		
		return "device/addDevice";
		
	}
	
	@PostMapping(value="/devices")
	public String addDevice(@ModelAttribute Device device, Model model) throws IOException, JadbException{
		
		//Check for invalid device
		if(device.getSerial().equals("")){
			return "device/deviceadderror";
		}
		
		//Add the device to the database
		deviceService.addDevice(device);
		
		//Set the device status
		device.setStatus(deviceService.getDeviceStatus(device.getSerial()));
		
		model.addAttribute("appiumstatus", deviceService.isAppiumRunning(device.getSerial()));
		model.addAttribute("deviceFile", new DeviceFile("NA"));  //Passing empty deviceFile object so that device file can be added/installed
		model.addAttribute("reservationID", 0);
		
		return "device/device";
		
	}
	
	//Get a device by ID
	@RequestMapping("/devices/{serial}")
	public String getDevice(@PathVariable String serial, Model model) throws IOException, JadbException{
		
			
		Device device = deviceService.getDevice(serial);
		
		//Check for invalid device
		if(device.getSerial().equals("")){
			return "device/deviceadderror";
		}
		
		model.addAttribute("device", device);
		model.addAttribute("packages", deviceService.getPackages(serial));
		model.addAttribute("deviceFile", new DeviceFile("NA"));  //Passing empty deviceFile object so that device file can be added/installed
		model.addAttribute("appiumstatus", deviceService.isAppiumRunning(serial));
		model.addAttribute("reservationID", 0);
				
		return "device/device";
	}
	
	//Get a device by ID
		@GetMapping("/devices/{serial}/edit")
		public String editDevice(@PathVariable String serial, Model model) throws IOException, JadbException{
			
			//Get list of filenames
			List<String> fileNames=storageService.getFileNames("C:\\toolbox\\Mobile_Bot\\mobile-bot\\src\\main\\resources\\static\\images\\devices");
				
			Device device = deviceService.getDevice(serial);
			
			//Check for invalid device
			if(device.getSerial().equals("")){
				return "device/deviceadderror";
			}
			
			model.addAttribute("device", device);
			model.addAttribute("packages", deviceService.getPackages(serial));
			model.addAttribute("imageFiles", fileNames);
					
			return "device/editDevice";
		}
	
		//Get a device by ID
		@GetMapping("/devices/{serial}/reserve")
		public String reserveDevice(@PathVariable String serial, Model model) throws Exception{
			
			LabManager sf = new LabManager();
			ReservationService rs = new ReservationService();
			ActiveReservation ar = new ActiveReservation();
			
			sf.Login("admin", "admin");
					
			ar = rs.createReservation(sf, deviceService.getDevice(serial), 45);
			
			//Get list of filenames
			List<String> fileNames=storageService.getFileNames("C:\\toolbox\\Mobile_Bot\\mobile-bot\\src\\main\\resources\\static\\images\\devices");
						
			Device device = deviceService.getDevice(serial);
					
			//Check for invalid device
			if(device.getSerial().equals("")){
				return "device/deviceadderror";
			}
					
			model.addAttribute("device", device);
			model.addAttribute("packages", deviceService.getPackages(serial));
			model.addAttribute("imageFiles", fileNames);
			model.addAttribute("deviceFile", new DeviceFile("NA"));  //Passing empty deviceFile object so that device file can be added/installed
			model.addAttribute("appiumstatus", deviceService.isAppiumRunning(serial));
			model.addAttribute("reservationID", ar.getReservationId());
							
			return "device/device";
		}
				
		
	//Get a device by ID
	@PostMapping("/devices/{id}/packages/install")
	public String installDevicePackage(@PathVariable String id, @ModelAttribute("devicefile") DeviceFile deviceFile, Model model) throws IOException, JadbException{
		
			
		Device device = deviceService.getDevice(id);
		
		//Check for invalid device
		if(device.getSerial().equals("")){
			return "device/deviceadderror";
		}
		

		//Install the apk on the device
		String installResult = this.deviceService.installDevicePackage(id, deviceFile.getName().trim());
		
		//Try to install the apk on the device.  If fails, return error
    	if(installResult.equalsIgnoreCase("success")){
    		model.addAttribute("message", "File Uploaded Successfully!");
    	}
    	else{
    		model.addAttribute("message", installResult);
    	}

		model.addAttribute("device", device);
		model.addAttribute("packages", deviceService.getPackages(id));
		model.addAttribute("deviceFile", new DeviceFile("NA"));  //Passing empty deviceFile object so that device file can be added/installed
		model.addAttribute("reservationID", 0);
		
		return "device/device";
	}
	
	@GetMapping(value="/devices/{id}/delete")
	public String deleteDevice(@PathVariable String id, Model model) throws IOException, JadbException{
		
		//Check for invalid device
		if(id.equals("")){
			return "device/deviceadderror";
		}
		
		//Add the device to the database
		deviceService.deleteDevice(id);
		
		model.addAttribute("devices", deviceService.getAllDevices());
		
		return "device/list";
		
	}
	
	@GetMapping(value="/devices/{id}/reboot")
	public ModelAndView rebootDevice(@PathVariable String id, Model model) throws IOException, JadbException{
		
		Device device = deviceService.getDevice(id);
		
		//Check for invalid device
		if(id.equals("")){
			return new ModelAndView("redirect:/device/deviceadderror");
		}
		
		//Reboot the device
		deviceService.rebootDevice(id);
		
		model.addAttribute("device", device);
		model.addAttribute("packages", deviceService.getPackages(id));
		model.addAttribute("deviceFile", new DeviceFile("NA"));  //Passing empty deviceFile object so that device file can be added/installed	
		
		return new ModelAndView("redirect:/devices/" + id);
		
	}
	
	@GetMapping(value="/devices/{id}/startappium")
	public ModelAndView startAppiumForDevice(@PathVariable String id, Model model) throws IOException, JadbException, AppiumNotStoppedException{
		
		Device device = deviceService.getDevice(id);
		String appiumstatus = deviceService.isAppiumRunning(id);
		
		//Check for invalid device
		if(id.equals("")){
			return new ModelAndView("redirect:/device/deviceadderror");
		}
		
		//Start Appium for device
		deviceService.startAppium(id);
		
		model.addAttribute("device", device);
		model.addAttribute("packages", deviceService.getPackages(id));
		model.addAttribute("deviceFile", new DeviceFile("NA"));  //Passing empty deviceFile object so that device file can be added/installed	
		model.addAttribute("appiumstatus", appiumstatus);
		
		return new ModelAndView("redirect:/devices/" + id);
		
	}
	
	@GetMapping(value="/devices/{id}/stopappium")
	public ModelAndView stopAppiumForDevice(@PathVariable String id, Model model) throws IOException, JadbException, AppiumNotStoppedException, ProcessNotKilledException {
		
		Device device = deviceService.getDevice(id);
		String appiumstatus = deviceService.isAppiumRunning(id);
		
		//Check for invalid device
		if(id.equals("")){
			return new ModelAndView("redirect:/device/deviceadderror");
		}
		
		//Start Appium for device
		deviceService.stopAppium(id);
		
		model.addAttribute("device", device);
		model.addAttribute("packages", deviceService.getPackages(id));
		model.addAttribute("deviceFile", new DeviceFile("NA"));  //Passing empty deviceFile object so that device file can be added/installed	
		model.addAttribute("appiumstatus", appiumstatus);
		
		return new ModelAndView("redirect:/devices/" + id);
		
	}
	
	@RequestMapping("/devices/list")
	public String list(Model model) throws IOException, JadbException{

		List<Device> deviceList = deviceService.getAllDevices();
		
		deviceList.sort((o1, o2) -> o1.getSerial().compareTo(o2.getSerial()));
		
		model.addAttribute("devices", deviceList);
		
		return "device/list";
		
	}
	
	@RequestMapping("/reservations/list")
	public String reslist(Model model) throws IOException, JadbException{

		List<Reservation> resList = resRepository.findAll();
		
		Comparator<Reservation> reservationComparator = (o1, o2)->o1.getId().compareTo(o2.getId());
		
		resList.sort(reservationComparator.reversed());
		
		model.addAttribute("reservations", resList);
		
		return "reservation/list";
		
	}
	
	@RequestMapping("/reservations/paglist")
	public String reslistPaginated(Model model) throws IOException, JadbException{
		
		return "reservation/paginatedlist";
		
	}
	
	@GetMapping(value="/devices/{id}/packages")
	public String getDevicePackages(@PathVariable String id, Model model) throws IOException, JadbException{
		model.addAttribute("device", deviceService.getDevice(id));
		model.addAttribute("packages", deviceService.getPackages(id));
		
		return "device/packages";
		
	}
	
	@GetMapping(value="/devices/{id}/packages/clear")
	public String clearDevicePackageCache(@PathVariable String id, @RequestParam("packageName") String packageName, Model model) throws IOException, JadbException{
			
			//Call method to clear cache
			deviceService.clearDevicePackageCache(id, packageName);
				
			Device device = deviceService.getDevice(id);
		
			model.addAttribute("device", device);
			model.addAttribute("packages", deviceService.getPackages(id));
			model.addAttribute("deviceFile", new DeviceFile("NA"));  //Passing empty deviceFile object so that device file can be added/installed	
				
			return "device/device";	
	}
	
	@GetMapping(value="/devices/{id}/packages/uninstall")
	public String uninstallDevicePackage(@PathVariable String id, @RequestParam("packageName") String packageName, Model model) throws IOException, JadbException{
			
			//Call method to uninstall package by name
			deviceService.uninstallDevicePackage(id, packageName);
				
			Device device = deviceService.getDevice(id);
		
			model.addAttribute("device", device);
			model.addAttribute("packages", deviceService.getPackages(id));
			model.addAttribute("deviceFile", new DeviceFile("NA"));  //Passing empty deviceFile object so that device file can be added/installed	
				
			return "device/device";	
	}
}
