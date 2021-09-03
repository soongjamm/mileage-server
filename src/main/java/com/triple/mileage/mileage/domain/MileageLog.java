package com.triple.mileage.mileage.domain;

import com.triple.mileage.review.common.BaseEntity;
import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class MileageLog extends BaseEntity {

	@Id @Type(type = "uuid-char")
	private UUID mileageId;
	private int point;

	@Type(type = "uuid-char")
	private UUID userId;

	@Type(type = "uuid-char")
	private UUID reviewId;

	@Type(type = "uuid-char")
	private UUID originReviewId;
	private String reason;

	protected MileageLog() {
	}

	public MileageLog(int point, UUID userId, UUID reviewId, UUID originReviewId, String reason) {
		this.mileageId = UUID.randomUUID();
		this.userId = userId;
		this.point = point;
		this.reviewId = reviewId;
		this.originReviewId = originReviewId;
		this.reason = reason;
	}

	@Override
	public LocalDateTime getCreatedDate() {
		return super.getCreatedDate();
	}
}
