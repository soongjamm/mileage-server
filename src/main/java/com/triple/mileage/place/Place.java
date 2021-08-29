package com.triple.mileage.place;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.UUID;

@Entity
public class Place {
	@Id
	private UUID placeId;
	private boolean reviewed;
	@Version
	private int version;
}
