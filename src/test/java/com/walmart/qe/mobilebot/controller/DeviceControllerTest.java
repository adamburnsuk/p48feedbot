package com.walmart.qe.mobilebot.controller;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.walmart.qe.mobilebot.data.DeviceRepository;
import com.walmart.qe.mobilebot.model.Device;

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
