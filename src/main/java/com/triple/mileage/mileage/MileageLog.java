package com.triple.mileage.mileage;

import com.triple.mileage.review.common.BaseEntity;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.UUID;

@Getter
@Entity
public class MileageLog extends BaseEntity {
	@Id
	private UUID mileageId;
	private int amount;
	private UUID reviewId;
	private String reason;

	public MileageLog() {
	}

	public MileageLog(int amount, UUID reviewId, String reason) {
		this.mileageId = UUID.randomUUID();
		this.amount = amount;
		this.reviewId = reviewId;
		this.reason = reason;
	}
}
