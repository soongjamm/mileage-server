package com.triple.mileage.review.infra;

import com.triple.mileage.review.application.ReviewOutbox;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface MileageClient {
	ResponseEntity<Void> requestMileage(List<ReviewOutbox> outboxes);
}
