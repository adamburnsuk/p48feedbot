package com.walmart.qe.mobilebot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This represents a device in the device repository and all fields associated with a mobile device.
 * 
 * @author Adam Burns
 * @version 1.0
 * 
 */
@Document(collection="Devices")
public class Device {

		@Id
		private String serial;
	    private String name;
	    private String model;
	    private String os;
	    private String osVersion;
	    private String status;
	    private String imageFile;
	    private String ip;
	    private String appiumPort;
	    private String chromedriverPort;
	    
	    /**
	     * Default constructor for a device
	     */
	    public Device() {

	    }

	    /**
	     * Constructor including required info
	     * 
	     * @param id the id of the device in the database
	     * @param name the name of the device (ie MC40 or Note5)
	     * @param status the connection status of the device
	     * @param imageFile the image file associated with the device (used for display in UI)
	     */
	    public Device(String id, String name, String status, String imageFile, String appiumPort, String ip) {
	        this.serial = id;
	        this.name = name;
	        this.status = status;
	        this.imageFile = imageFile;
	        this.ip = ip;
	        this.appiumPort = appiumPort;
	    }

	    /**
	     * This method gets the name of the image file associated with this device.
	     * 
	     * @return returns the name of the image file associated with this device
	     */
		public String getImageFile() {
			return imageFile;
		}

		/**
		 * 
		 * @param imageFile the name of the image file associated with this device 
		 */
		public void setImageFile(String imageFile) {
			this.imageFile = imageFile;
		}

		/**
		 * Get the name of the device
		 * 
		 * @return
		 */
		public String getName() {
			return name;
		}

		/**
		 * Set the name of the device
		 * 
		 * @param name the name of the device
		 */
		public void setName(String name) {
			this.name = name;
		}

		/**
		 * Gets the serial number of the device
		 * 
		 * @return
		 */
		public String getSerial() {
			return serial;
		}

		/**
		 * Sets the serial number of the device
		 * 
		 * @param serial the unique serial number for the device
		 */
		public void setSerial(String serial) {
			this.serial = serial;
		}

		/**
		 * Gets the ADB status of the device (is it connected and ready in ADB)
		 * 
		 * @return (ADB statuses)
		 */
		public String getStatus() {
			return status;
		}

		/**
		 * Set the ADB status of the device
		 * 
		 * @param status the status of the device in ADB (Offline, Ready, etc)
		 */
		public void setStatus(String status) {
			this.status = status;
		}

		@JsonIgnore
		public String getIp() {
			return ip;
		}

		@JsonProperty
		public void setIp(String ip) {
			this.ip = ip;
		}
		
		@JsonIgnore
		public String getAppiumPort() {
			return appiumPort;
		}

		@JsonProperty
		public void setAppiumPort(String appiumPort) {
			this.appiumPort = appiumPort;
		}

		public String getModel() {
			return model;
		}

		public void setModel(String model) {
			this.model = model;
		}

		public String getOs() {
			return os;
		}

		public void setOs(String os) {
			this.os = os;
		}

		public String getOsVersion() {
			return osVersion;
		}

		public void setOsVersion(String osVersion) {
			this.osVersion = osVersion;
		}

		public String getChromedriverPort() {
			return chromedriverPort;
		}

		public void setChromedriverPort(String chromedriverPort) {
			this.chromedriverPort = chromedriverPort;
		}
	
}
