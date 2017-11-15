package com.walmart.qe.mobilebot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.walmart.qe.mobilebot.model.Reservation;
import com.walmart.qe.mobilebot.service.ReservationService;

@RestController
public class ReservationRestController {

	@Autowired
	private ReservationService resService;
	
	@RequestMapping(path="/svc/reservations/list", method=RequestMethod.GET)
	public List<Reservation> getAllEmployees(){
		return resService.getAllReservations();
	}
	
    @RequestMapping(value = "/svc/reservations/{id}", method = RequestMethod.GET)
	public Reservation getReservationById(@PathVariable("id") String id){
		return resService.getReservationById(id);
	}
	
}
