package com.walmart.qe.mobilebot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.EXPECTATION_FAILED, reason="Appium Not Successfully Stopped")  // 417
public class AppiumNotStoppedException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public AppiumNotStoppedException(String message) {
        super(message);
    }

    public AppiumNotStoppedException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
