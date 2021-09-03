package com.triple.mileage.place;

import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Version;
import java.util.UUID;

@Getter
@Entity
public class Place {
	@Id @Type(type = "uuid-char")
	private UUID placeId;
	private boolean reviewed;

	@Version
	private int version;

	protected Place() {

	}

	public Place(UUID placeId) {
		this.placeId = placeId;
	}

	public void reviewed() {
		this.reviewed = true;
	}

	public void notReviewed(){
		this.reviewed = false;
	}
}
