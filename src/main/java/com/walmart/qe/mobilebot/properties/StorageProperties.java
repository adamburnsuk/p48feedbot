package com.walmart.qe.mobilebot.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * This class represents the properties for storage in this application including the path to upload folder
 * 
 * @author Adam Burns
 * @version 1.0
 *
 */
@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "C:\\toolbox\\Mobile_Bot\\files";

    /**
     * Get the folder location for storing files
     * 
     * @return
     */
    public String getLocation() {
        return location;
    }

    /**
     * Set the folder location for storing files
     * 
     * @param location the folder name or path to the folder for storing files for application
     */
    public void setLocation(String location) {
        this.location = location;
    }

}
