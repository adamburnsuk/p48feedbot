package com.walmart.qe.mobilebot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.EXPECTATION_FAILED, reason="Appium Not Successfully Started")  // 417
public class ProcessNotKilledException extends Exception {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public ProcessNotKilledException(String message) {
        super(message);
    }

    public ProcessNotKilledException(String message, Throwable cause) {
        super(message, cause);
    }
	
}
