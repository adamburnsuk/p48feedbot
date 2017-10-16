package com.walmart.qe.mobilebot.model;

/**
 * Represents a file on a device or to be installed on a device
 * 
 * @author Adam Burns
 * @version 1.0
 */
public class DeviceFile {

	private String name;
	
	/**
	 * Default constructor for a file object
	 */
	//Default constructor
    public DeviceFile() {

    }

    /**
     * Constructor for device file with name provided
     * 
     * @param name the name of the file (not the path, just the name of the file itself)
     */
    public DeviceFile(String name) {
        this.name = name;
    }

    /**
     * Set the name of the file
     * 
     * @param name the name of the file (not the path)
     */
	public void setName(String name) {
		this.name=name;
	}
	
	/**
	 * Get the name of the file
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
}
