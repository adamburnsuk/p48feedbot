package com.walmart.qe.mobilebot.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="Reservations")
public class Reservation {

	@Id
	private String id;
	
}
