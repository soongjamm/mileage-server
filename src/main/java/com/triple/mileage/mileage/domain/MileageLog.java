package com.triple.mileage.mileage.domain;

import lombok.Getter;
import org.hibernate.annotations.Type;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
public class MileageLog {

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
	private LocalDateTime createdDate = LocalDateTime.now(); // JPAAudit 이 갑자기 동작하지 않아서 임시로 생성

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

}
