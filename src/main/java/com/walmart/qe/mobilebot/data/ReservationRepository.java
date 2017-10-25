package com.walmart.qe.mobilebot.data;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.walmart.qe.mobilebot.model.Reservation;

public interface ReservationRepository extends MongoRepository <Reservation, String> {

	public Reservation findById(String id);
	
}
