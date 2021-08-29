package com.triple.mileage.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Entity
public class User {
	@Id
	private UUID userId;
}
