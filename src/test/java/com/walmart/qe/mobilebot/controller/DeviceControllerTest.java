package com.walmart.qe.mobilebot.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.walmart.qe.mobilebot.data.DeviceRepository;
import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.service.DeviceService;
import com.walmart.qe.mobilebot.service.StorageService;

public class DeviceControllerTest {


	@Mock
	private DeviceRepository deviceRepository;
	
	@InjectMocks
	private DeviceController deviceController;
	
	private MockMvc mockMvc;
	
	//@Before
	public void setup(){
		MockitoAnnotations.initMocks(this);
		
		mockMvc = MockMvcBuilders.standaloneSetup(deviceController).build();
	}
	
	//@Test
	public void getDevicesTest() throws Exception{
		List<Device> devices = new ArrayList<>();
		devices.add(new Device());
		devices.add(new Device());
		
		Mockito.when(deviceRepository.findAll()).thenReturn(devices);
		
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/svc/devices")
								     .accept(MediaType.APPLICATION_JSON))
								     .andReturn();
		
		System.out.println(mvcResult.getResponse());
	
		
	}
	
}
