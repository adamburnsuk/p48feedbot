package com.walmart.qe.mobilebot.stelabapi;

public class NoAvailableDeviceException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NoAvailableDeviceException(String message) {
        super(message);
    }

    public NoAvailableDeviceException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
