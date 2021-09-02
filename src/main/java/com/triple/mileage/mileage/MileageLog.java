package com.triple.mileage.mileage;

import com.triple.mileage.review.common.BaseEntity;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class MileageLog extends BaseEntity {
	@Id
	private UUID mileageId;
	private int amount;
	private UUID userId;
	private UUID reviewId;
	private String reason;

	public MileageLog() {
	}

	@Builder
	public MileageLog(int amount, UUID userId, UUID reviewId, String reason) {
		this.mileageId = UUID.randomUUID();
		this.userId = userId;
		this.amount = amount;
		this.reviewId = reviewId;
		this.reason = reason;
	}

	@Override
	public LocalDateTime getCreatedDate() {
		return super.getCreatedDate();
	}
}
