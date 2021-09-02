package com.triple.mileage.review.infra;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.triple.mileage.common.Utility.baseObjectMapper;

@NoArgsConstructor
@Getter
public class ReviewModified {
	private UUID originalReviewId;
	private UUID modifiedReviewId;
	private LocalDateTime occurredTime;

	public ReviewModified(UUID originalReviewId, UUID modifiedReviewId) {
		this.originalReviewId = originalReviewId;
		this.modifiedReviewId = modifiedReviewId;
		this.occurredTime = LocalDateTime.now();
	}

	public String payload() {
		try {
			return baseObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			throw new IllegalStateException("payload를 생성하는데 실패하였습니다.");
		}
	}
}
