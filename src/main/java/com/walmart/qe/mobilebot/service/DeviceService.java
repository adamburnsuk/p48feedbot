package com.walmart.qe.mobilebot.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.qe.mobilebot.data.DeviceRepository;
import com.walmart.qe.mobilebot.exceptions.AppiumNotStoppedException;
import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.model.DevicePackage;
import com.walmart.ste.stelabapi.*;
import com.walmart.qe.mobilebot.util.AppiumServerJava;

import se.vidstige.jadb.JadbConnection;
import se.vidstige.jadb.JadbDevice;
import se.vidstige.jadb.JadbException;
import se.vidstige.jadb.managers.PackageManager;
import se.vidstige.jadb.managers.Package;

/**
 * This class contains all methods required to work with a device
 * 
 * You can create, read, update, delete devices, as well as interact with physical devices using these methods.
 * 
 * @author Adam Burns (a2burns)
 * @version 1.0
 * 
 */
@Service
public class DeviceService {
	
	@Autowired
	private DeviceRepository deviceRepository;
	
	LabManager labmanager = new LabManager();
	
	/**
	 * Get a list of all devices in the database
	 * 
	 * @return
	 * @throws IOException
	 * @throws JadbException this exception is related to ADB issues trying to communicate with the device
	 */
	public List<Device> getAllDevices() throws IOException, JadbException{
		
		//Create arraylist to hold the list of devices returned by the database
		List<Device> devices = new ArrayList<>();
		
		//Get all devices from the database and convert to device List using lamda expression (java8)
		deviceRepository.findAll().forEach(devices::add);
		
		//Update statuses from ADB before returning list
		for(Device d : devices){
			d.setStatus(this.getDeviceStatus(d.getSerial()));
		}
		
		//Return the list of devices
		return devices;
	}
	
	/**
	 * Delete all devices from the database
	 */
	public void deleteAllDevices(){
		deviceRepository.deleteAll();
	}
	
	/**
	 * Get a particular device by id
	 * 
	 * @param id the unique ID of the device
	 * @return
	 * @throws IOException
	 * @throws JadbException
	 */
	public Device getDevice(String id) throws IOException, JadbException{
		
			Device device;

			device = deviceRepository.findOne(id);
			
			//Set the realtime status of the device
			device.setStatus(this.getDeviceStatus(device.getSerial()));
			
			//Find the device by ID using the findOne method of DeviceRepository, and return it
			return device;
			
			
	}

	/**
	 * Add a device to the database
	 * 
	 * @param device the device object that you want to add
	 */
	public void addDevice(Device device) {
		
		//Save the new device to the database using the DeviceRepository save method.
		deviceRepository.save(device);
		
	}

	/**
	 * Update a device in the database
	 * 
	 * @param id the unique id of the device that you want to update
	 * @param device the new device object to replace the old device info
	 */
	public void updateDevice(String id, Device device) {
		
		//Save the updated device to the database using the DeviceRepository save method.
		deviceRepository.save(device);
		
	}

	/**
	 * Delete a device from the database
	 * 
	 * @param id the unique ID of the device that you want to delete
	 */
	public void deleteDevice(String id) {

		deviceRepository.delete(id);
		
	}
	
	/**
	 * Capture a screen recording to the device
	 * 
	 * @param deviceID the unique ID of the device that you want to record
	 * @throws Exception
	 */
	public void recordVideo(String deviceID) throws Exception {
		JadbConnection jadb = new JadbConnection();
    	List<JadbDevice> devices = jadb.getDevices();
    	
    	devices.get(0).executeShell("screenrecord", "/sdcard/screenshots/video_1.mp4 --timelimit 10");
	}
	
	
	/**
	 * Reboot android device by ID (This is the ID stored in the database)
	 * 
	 * @param id the unique ID of the device you want to reboot
	 * @throws IOException
	 * @throws JadbException Android Device Bridge exeption
	 */
	public void rebootDevice(String id) throws IOException, JadbException {
		JadbConnection jadb = new JadbConnection();
    	List<JadbDevice> devices = jadb.getDevices();
    	JadbDevice jDevice = null;
	    
		for(JadbDevice d : devices){
			if(d.getSerial().equals(this.getDevice(id).getSerial())){
				jDevice=d;
			}
		}
    	
    	jDevice.executeShell("reboot", "");	  
	}
	
	/**
	 * Reboot android device by ID (This is the ID stored in the database)
	 * 
	 * @param id the unique ID of the device in the database
	 * 
	 * @param packageName the name of the package that you want to clear
	 * @return
	 * @throws IOException
	 * @throws JadbException
	 */
	public String clearDevicePackageCache(String id, String packageName) throws IOException, JadbException {
		JadbConnection jadb = new JadbConnection();
		List<JadbDevice> devices = jadb.getDevices();
		InputStream connection;

		JadbDevice jDevice = null;

		try{
			for(JadbDevice d : devices){
				if(d.getSerial().equals(this.getDevice(id).getSerial())){
					jDevice=d;
				}
			}
		}
		catch(Exception e){

			if(e.getMessage().contains("device unauthorized")){
				System.out.println(e.getMessage());
				return "Unauthorized";
			}
			else if(e.getMessage().contains("Connection refused")){
				return "ADB Connection Offline";
			}	
			else{
				return "Offline";
			}

		}

		connection = jDevice.executeShell("pm clear", packageName.trim());	 

		//Get input stream text
		String result = new BufferedReader(new InputStreamReader(connection)).lines()
				.parallel().collect(Collectors.joining("\n"));

		System.out.println(result);

		connection.close();
		
		return result;
	}
	
	    /**
	     * Uninstall package from android device by package name and device ID (This is the ID stored in the database)
	     * 
	     * @param id the unique ID of the device in the database
	     * @param packageName the name of the package you want to uninstall
	     * @return
	     * @throws IOException
	     * @throws JadbException
	     */
		public String uninstallDevicePackage(String id, String packageName) throws IOException, JadbException {
			
			JadbConnection jadb = new JadbConnection();
			List<JadbDevice> devices = jadb.getDevices();
			InputStream connection;

			JadbDevice jDevice = null;

			try{
				for(JadbDevice d : devices){
					if(d.getSerial().equals(this.getDevice(id).getSerial())){
						jDevice=d;
					}
				}
			}
			catch(Exception e){

				if(e.getMessage().contains("device unauthorized")){
					System.out.println(e.getMessage());
					return "Unauthorized";
				}
				else if(e.getMessage().contains("Connection refused")){
					return "ADB Connection Offline";
				}	
				else{
					return "Offline";
				}

			}

			connection = jDevice.executeShell("pm uninstall", packageName.trim());	 

			//Get input stream text
			String result = new BufferedReader(new InputStreamReader(connection)).lines()
					.parallel().collect(Collectors.joining("\n"));

			System.out.println(result);

			connection.close();
			
			return result;
		}
		
	/**
	 * Uninstall package from android device by package name and device ID (This is the ID stored in the database)
	 * 
	 * @param id the unique ID of the device in the database
	 * @param fileName the name of the application file that you want to install on the device
	 * @return
	 * @throws IOException
	 * @throws JadbException
	 */
	public String installDevicePackage(String id, String fileName) throws IOException, JadbException {
			
			JadbConnection jadb = new JadbConnection();
			List<JadbDevice> devices = jadb.getDevices();
			PackageManager p;

			JadbDevice jDevice = null;

			try{
				for(JadbDevice d : devices){
					if(d.getSerial().equals(this.getDevice(id).getSerial())){
						jDevice=d;
					}
				}
			}
			catch(Exception e){

				if(e.getMessage().contains("device unauthorized")){
					System.out.println(e.getMessage());
					return "Unauthorized";
				}
				else if(e.getMessage().contains("Connection refused")){
					return "ADB Connection Offline";
				}	
				else{
					return "Offline";
				}

			}

			p = new PackageManager(jDevice);
			
			try{
				p.forceInstall(new File("C:\\toolbox\\Mobile_Bot\\files\\" + fileName));
			}
			catch(IOException e){
				return "Fail: " + e.getMessage();
			}
			catch(JadbException e){
				return "Fail: " + e.getMessage();
			}

			return "Success";
	}
	
	/**
	 * Use this method to get the actual ADB status of the device (Takes the database ID of the device as argument)
	 * 
	 * @param id the unique ID of the device in the database
	 * @return
	 * @throws IOException
	 * @throws JadbException
	 */
	public String getDeviceStatus(String id) throws IOException, JadbException{
		
		try{
			JadbConnection jadb = new JadbConnection();
			List<JadbDevice> devices = jadb.getDevices();
		
			Device device = deviceRepository.findOne(id);
	    
			for(JadbDevice d : devices){
	    	
	    		
	    			if(d.getSerial().equals(device.getSerial())){
	    		
	    				if(d.getState().toString().equals("Device")){
	    					return "Ready";
	    				}
	    		
	    				return d.getState().toString();
	    			}
	    	}
		}
	    catch(Exception e){
	    		
	    		if(e.getMessage().contains("device unauthorized")){
	    			System.out.println(e.getMessage());
	    			return "Unauthorized";
	    		}
	    		else if(e.getMessage().contains("Connection refused")){
	    			return "ADB Connection Offline";
	    		}	
	    		else{
	    			return "Offline";
	    		}
	    		
	    }

	    
	    //If device not found at all via adb, return Offline
	    return "Offline";
	    
	    
	}
	
	/**
	 * This method executes adb command and returns list of packages found on device
	 * 
	 * @param id the unique ID of the device in the database
	 * @return
	 * @throws IOException
	 * @throws JadbException
	 */
	public List<DevicePackage> getPackages(String id) throws IOException, JadbException{
		
		List<DevicePackage> devicePackages = new ArrayList<DevicePackage>();
		
		try{
			
			JadbConnection jadb = new JadbConnection();
			List<JadbDevice> devices = jadb.getDevices();
			List<Package> packages = new ArrayList<Package>();
			JadbDevice jDevice = null;
    	
			for(JadbDevice d : devices){
				if(d.getSerial().equals(this.getDevice(id).getSerial())){
					jDevice=d;
				}
			}
    	
			packages = new PackageManager(jDevice).getPackages();
    	
			for(Package p : packages){
				devicePackages.add(new DevicePackage(p.toString()));
			}
		}
		catch(Exception e){
			System.out.println("Device not connected.");
		}
		
		return devicePackages;
		
	}

	public boolean startAppium(String id) throws MalformedURLException {
		
		AppiumServerJava ap = new AppiumServerJava();
		Device device = deviceRepository.findOne(id);
		
		ap.startServer(device.getIp(), device.getAppiumPort(), device.getSerial(), device.getChromedriverPort());
		
		//Check to make sure success in starting appium
		if(ap.checkAppiumRunning(device.getIp(), device.getAppiumPort(),30000)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public boolean startAppium(ActiveReservation reservation) throws ClientProtocolException, IOException, NoAvailableDeviceException {
		
		AppiumServerJava ap = new AppiumServerJava();
		Device device = deviceRepository.findOne(reservation.getDeviceManufacturer());
		
		ap.startServer(device.getIp(), device.getAppiumPort(), device.getSerial(), device.getChromedriverPort(), reservation.getReservationId().toString());
		
		//Check to make sure success in starting appium
		if(ap.checkAppiumRunning(device.getIp(), device.getAppiumPort(),30000)){
			return true;
		}else{
			return false;
		}
		
	}
	
	public void stopAppium(String id) throws MalformedURLException, AppiumNotStoppedException {
		
		AppiumServerJava ap = new AppiumServerJava();
		Device device = deviceRepository.findOne(id);
		
		if(ap.checkAppiumRunning(device.getIp(), device.getAppiumPort(), 8000)){
			ap.stopServer(device.getAppiumPort());
		}
		
		if(ap.checkAppiumRunning(device.getIp(), device.getAppiumPort(), 8000)){
			throw new AppiumNotStoppedException("Appium could not be stopped for device.");
		}
		
	}
	
	public void stopAppium(ActiveReservation reservation) throws MalformedURLException, AppiumNotStoppedException {
		
		AppiumServerJava ap = new AppiumServerJava();
		Device device = deviceRepository.findOne(reservation.getDeviceManufacturer());
		
		if(ap.checkAppiumRunning(device.getIp(), device.getAppiumPort(), 8000)){
			ap.stopServer(device.getAppiumPort());
		}
		
		if(ap.checkAppiumRunning(device.getIp(), device.getAppiumPort(), 8000)){
			throw new AppiumNotStoppedException("Appium could not be stopped for device.");
		}
		
	}
	
	public String isAppiumRunning(String id) throws MalformedURLException {
		
		AppiumServerJava ap = new AppiumServerJava();
		Device device = deviceRepository.findOne(id);
		
		if(ap.checkAppiumRunning(device.getIp(), device.getAppiumPort(), 2000)){
			return "Connected";
		}else{
			return "Disconnected";
		}
		
	}
}
