package com.triple.mileage.common;

import com.triple.mileage.review.application.ReviewAction;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Entity
public class ReviewOutbox implements Serializable {
	@Id @Type(type = "uuid-char")
	private UUID outboxId;

	@Type(type = "uuid-char")
	private UUID reviewId;

	@Enumerated(EnumType.STRING)
	private ReviewAction action;
	private String payload;

	protected ReviewOutbox() {

	}

	@Builder
	public ReviewOutbox(UUID reviewId, ReviewAction action, String payload) {
		this.outboxId = UUID.randomUUID();
		this.reviewId = reviewId;
		this.action = action;
		this.payload = payload;
	}
}
