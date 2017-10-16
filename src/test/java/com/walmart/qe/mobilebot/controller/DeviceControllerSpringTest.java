package com.walmart.qe.mobilebot.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.walmart.qe.mobilebot.data.DeviceRepository;
import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.service.DeviceService;
import com.walmart.qe.mobilebot.service.StorageService;

@RunWith(SpringRunner.class)
@WebMvcTest(DeviceController.class)
public class DeviceControllerSpringTest {

	@Autowired
	MockMvc mockMvc;
	
	@MockBean DeviceRepository deviceRepository;
	@MockBean DeviceService deviceService;
	@MockBean StorageService storageService;
	
	
	@Test
	public void getDevicesListTest() throws Exception{
		
		Device a = new Device();
		Device b = new Device();
		
		a.setName("Test Device a");
		b.setName("Test Device b");
		
		List<Device> deviceList = new ArrayList<Device>();
		
		deviceList.add(a);
		deviceList.add(b);
		
		Mockito.when(deviceRepository.findAll()).thenReturn(deviceList);
		
		mockMvc.perform(MockMvcRequestBuilders.get(new URI("/svc/devices"))
			   .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	
		
	}
	
	@Test
	public void getDeviceByIDTest() throws Exception{
		
		Device testDevice = new Device();
		testDevice.setSerial("12345");
		
		Mockito.when(deviceRepository.findOne("1234")).thenReturn(testDevice);
		
		mockMvc.perform(MockMvcRequestBuilders.get(new URI("/svc/devices/1234"))
			   .accept(MediaType.APPLICATION_JSON))
			   .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
	
		
	}
	
	
	
}
