package com.walmart.qe.mobilebot.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.net.UrlChecker;

import com.walmart.qe.mobilebot.exceptions.AppiumNotStoppedException;

public class AppiumServerJava {
	
	/**
	 * 
	 * @param ip the ip address where you want to start appium (This will generally be 127.0.0.1
	 * @param port the port where you want to start appium
	 * @param udid the device id of the device you want to connect to the appium server (This device must be available and authorized in adb)
	 * @return returns a process object
	 */
	public Process startServer(String ip, String port, String udid, String chromedriverport) {
		String[] command = {"CMD", "/C", "start", 
				            "cmd.exe", "/k", 
				            "C://toolbox//Mobile_Bot//mobile-bot//bat//startappium",
				            ip,
				            port,
				            udid,
				            chromedriverport};

		ProcessBuilder probuilder = new ProcessBuilder(command);
		Process p = null;
		
		try {
			p = probuilder.start(); 
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return p;
	}
	
	/**
	 * 
	 * @param ip the ip address where you want to start appium (This will generally be 127.0.0.1
	 * @param port the port where you want to start appium
	 * @param udid the device id of the device you want to connect to the appium server (This device must be available and authorized in adb)
	 * @param chromedriverport the port that you want to use for chromedriver for appium session on this device
	 * @param reservationID the reservation ID of the active reservation for device  (This is used to save logs for appium session)
	 * @return returns a process object
	 */
	public Process startServer(String ip, String port, String udid, String chromedriverport, String reservationID) {
		String[] command = {"CMD", "/C", "start", 
				            "cmd.exe", "/k", 
				            "C://toolbox//Mobile_Bot//mobile-bot//bat//startappium",
				            ip,
				            port,
				            udid,
				            chromedriverport,
				            reservationID};

		ProcessBuilder probuilder = new ProcessBuilder(command);
		Process p = null;
		
		try {
			p = probuilder.start(); 
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		return p;
	}
	
	/**
	 * 
	 * @param port port for the appium/selenium server that you want to stop
	 */
	public void stopServer(String port) throws AppiumNotStoppedException {
		
		try {
			
			String childPID = findPIDForPort(port);
			String parentPID = findParentForPID(childPID);
			
			ProcessBuilder parent = new ProcessBuilder("taskkill", "/pid", parentPID, "/f");
			ProcessBuilder child = new ProcessBuilder("taskkill", "/pid", childPID, "/f");
			
			parent.start();
			child.start();
			
		} catch (Exception e) {
			throw new AppiumNotStoppedException("Could not stop Appium for device.");
		}
	}
	
	
	/**
	 * 
	 * @param ip the ip address that appium should be running on
	 * @param port the port number that appium should be running on
	 * @param duration the duration in milliseconds to wait for appium response
	 * @return
	 * @throws MalformedURLException
	 */
	public boolean checkAppiumRunning(String ip, String port, long duration) throws MalformedURLException{

		String SERVER_URL = String.format("http://%s:%s/wd/hub", ip, port);
		final URL status = new URL(SERVER_URL + "/sessions");
		        
		try {
			new UrlChecker().waitUntilAvailable(duration, TimeUnit.MILLISECONDS, status);
		    return true;
		} catch (UrlChecker.TimeoutException e) {
		    return false;
		}
	}
	
	/**
	 * 
	 * @param port this listening port that should have a process attached to it (this will only find LISTENING port PIDs..not any other type of traffic) 
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String findPIDForPort(String port) throws IOException, InterruptedException{
		
		int bytesRead = -1;
		byte[] bytes = new byte[1024];
		String output = "";
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", "netstat -a -n -o | findstr :" + port);
	    Process process = builder.start();
	    boolean pidLine = false;
	    String pid = "";
	    
	    while((bytesRead = process.getInputStream().read(bytes)) > -1){
			output = output + new String(bytes, 0, bytesRead);
		}
		
		output = output.replace("\\r", " ").replace("\\n", " ").replace("\r", " ").replace("\n", " ").trim();
				
		String[] outputArray = output.split(" ");
		
		//Loop through the array to find the correct PID (Should be LISTENING)
		for(String a : outputArray){
			
			if(pidLine){
				if(!a.equalsIgnoreCase("")){
					pid = a.trim();
					pidLine=false;
					System.out.println(pid);
					return pid;
				}
			}
			
			if(a.equalsIgnoreCase("listening")){
				pidLine=true;
			}
		}
	    
		return pid;
	}
	
	/**
	 * 
	 * @param pid the PID that you want to find parent for
	 * @return
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public String findParentForPID(String pid) throws IOException, InterruptedException{
		
		int bytesRead = -1;
		byte[] bytes = new byte[1024];
		String output = "";
		ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/C", "wmic process where (processid=" + pid + ") get parentprocessid");
	    Process process = builder.start();
	    
	    while((bytesRead = process.getInputStream().read(bytes)) > -1){
			output = output + new String(bytes, 0, bytesRead);
		}
		
		output = output.replace("\\r", " ").replace("\\n", " ").replace("\r", " ").replace("\n", " ");
		
		String[] outputArray = output.split(" ");
		
		//Get the last value...will be port
		String parentPid = outputArray[outputArray.length-1].trim();
	    
		System.out.println(parentPid);
		
		return parentPid;
	}
	
	
}
