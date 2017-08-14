package com.walmart.qe.mobilebot.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.service.DeviceService;

@Controller
public class ViewController {

	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping("/")
	public String index(Model model){
		
		model.addAttribute("datetime", new Date());
		
		return "index";
		
	}
	
	@GetMapping(value="/device")
	public String getDevice(Model model){
		model.addAttribute("device", new Device());
		
		return "device/addDevice";
		
	}
	
	@PostMapping(value="/device")
	public String addDevice(@ModelAttribute Device device){
		
		//Check for invalid device
		if(device.getId().equals("")){
			return "device/deviceadderror";
		}
		
		//Add the device to the database
		deviceService.addDevice(device);
		
		return "device/device";
		
	}
	
	@RequestMapping("/list")
	public String list(Model model){

		model.addAttribute("devices", deviceService.getAllDevices());
		
		return "device/list";
		
	}
}
