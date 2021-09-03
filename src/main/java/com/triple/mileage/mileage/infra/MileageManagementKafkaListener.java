package com.triple.mileage.mileage.infra;

import com.triple.mileage.review.application.ReviewOutbox;
import com.triple.mileage.mileage.application.MileageManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MileageManagementKafkaListener {

	private final MileageManagementService mileageManagementService;

	@KafkaListener(topics = "reviewed", groupId = "reviewed", containerFactory = "reviewOutboxKafkaContainer")
	public void reviewOutboxListener(ReviewOutbox reviewOutbox) {
		mileageManagementService.handle(reviewOutbox);
	}

}
