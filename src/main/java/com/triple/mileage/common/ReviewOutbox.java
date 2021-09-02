package com.triple.mileage.common;

import com.triple.mileage.review.application.ReviewAction;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Entity
public class ReviewOutbox implements Serializable {
	@Id
	private UUID outboxId;
	private UUID reviewId;
	private ReviewAction action;
	private String payload;

	public ReviewOutbox() {

	}

	@Builder
	public ReviewOutbox(UUID reviewId, ReviewAction action, String payload) {
		this.outboxId = UUID.randomUUID();
		this.reviewId = reviewId;
		this.action = action;
		this.payload = payload;
	}
}
