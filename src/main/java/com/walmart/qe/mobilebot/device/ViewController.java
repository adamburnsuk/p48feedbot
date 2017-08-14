package com.walmart.qe.mobilebot.device;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ViewController {

	@Autowired
	private DeviceService deviceService;
	
	@RequestMapping("/")
	public String index(Model model){
		
		model.addAttribute("datetime", new Date());
		
		return "index";
		
	}
	
	@RequestMapping("/list")
	public String list(Model model){

		model.addAttribute("devices", deviceService.getAllDevices());
		
		System.out.println(deviceService.getAllDevices().get(0).getImageFile());
		
		return "list";
		
	}
}
