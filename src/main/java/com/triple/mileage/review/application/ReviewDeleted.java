package com.triple.mileage.review.application;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class ReviewDeleted {
	private UUID createdReviewId;
	private LocalDateTime occurredTime;

	public ReviewDeleted(UUID deletedReviewId) {
		this.createdReviewId = deletedReviewId;
		this.occurredTime = LocalDateTime.now();
	}

	public String payload() {
		try {
			return new ObjectMapper().disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).registerModule(new JavaTimeModule()).writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("payload를 생성하는데 실패하였습니다.");
		}
	}
}
