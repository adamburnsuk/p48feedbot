package com.walmart.qe.mobilebot.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.walmart.qe.mobilebot.data.ReservationRepository;
import com.walmart.qe.mobilebot.model.Device;
import com.walmart.qe.mobilebot.model.Reservation;
import com.walmart.ste.stelabapi.ActiveReservation;
import com.walmart.ste.stelabapi.NoAvailableDeviceException;
import com.walmart.ste.stelabapi.ReservationResponse;
import com.walmart.ste.stelabapi.LabManager;

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

	
	@Autowired
	private ReservationRepository resRepository;
	
	
	public List<Reservation> getAllReservations(){
		return resRepository.findAll();
	}
	
	public Reservation getReservationById(String id){
		return resRepository.findById(id);
	}
	
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
