package com.walmart.qe.mobilebot.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.springframework.stereotype.Service;

import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.stelabapi.ActiveReservation;
import com.walmart.qe.mobilebot.stelabapi.NoAvailableDeviceException;
import com.walmart.qe.mobilebot.stelabapi.ReservationResponse;
import com.walmart.qe.mobilebot.stelabapi.LabManager;

/**
 * This class contains all methods required to work with a device reservation
 * 
 * You can create, read, update, delete reservations.
 * 
 * @author Adam Burns (a2burns)
 * @version 1.0
 * 
 */
@Service
public class ReservationService {

	/**
	 * 
	 * @param sf
	 * @param device
	 * @param duration
	 * @return
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws NoAvailableDeviceException
	 */
	public ActiveReservation createReservation(LabManager sf, Device device, int duration) throws ClientProtocolException, IOException, NoAvailableDeviceException {
		
		//Create a reservation by device serial number
		ReservationResponse rr = sf.createReservationByDeviceSerial(device.getSerial(), duration);
		
		//Get the reservation response
		ActiveReservation ar = rr.getActiveReservation();
		
		return ar;
	}
	
	
}
