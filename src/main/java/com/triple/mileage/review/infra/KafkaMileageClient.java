package com.triple.mileage.review.infra;

import com.triple.mileage.common.ReviewOutbox;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class KafkaMileageClient implements MileageClient {

	private final KafkaTemplate<String, ReviewOutbox> kafkaTemplate;

	@Override
	public ResponseEntity<Void> requestMileage(List<ReviewOutbox> outboxes) {
		for (ReviewOutbox outbox : outboxes) {
			kafkaTemplate.send("reviewed", outbox);
		}
		return ResponseEntity.ok().build();
	}
}
