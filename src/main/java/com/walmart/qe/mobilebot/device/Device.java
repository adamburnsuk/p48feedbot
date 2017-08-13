package com.walmart.qe.mobilebot.device;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="Devices")
public class Device {

		@Id
	    private String id;
	    private String name;
	    private String status;
	    
	    
	    //Default constructor
	    public Device() {

	    }

	    
	    public Device(String id, String name, String status) {
	        this.id = id;
	        this.name = name;
	        this.status = status;
	    }

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getStatus() {
			return status;
		}

		public void setStatus(String status) {
			this.status = status;
		}
	
}
