package com.walmart.qe.mobilebot.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class CleanupProcesses {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

	@Scheduled(fixedRate = 300000)
	public void reportCurrentTime() {
	   System.out.println("The time is now {}" + dateFormat.format(new Date()));
	}
	
	@Scheduled(fixedRate=300000)
	public void checkAppiumConnections(){
		
	}
}
	