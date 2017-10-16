package com.walmart.qe.mobilebot.model;

/**
 * Represents a package (application) on a device
 * 
 * @author Adam Burns
 * @version 1.0
 * 
 */
public class DevicePackage {

	private String name;
	
	/**
	 * Default constructor
	 */
	//Default constructor
    public DevicePackage() {

    }

    /**
     * Constructor passing in the package name
     * 
     * @param name the name of the package
     */
    public DevicePackage(String name) {
        this.name = name;
    }

    /**
     * Set the name of the package
     * 
     * @param name the name of the package
     */
	public void setName(String name) {
		this.name=name;
	}
	
	/**
	 * Get the name of the package
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}
}
