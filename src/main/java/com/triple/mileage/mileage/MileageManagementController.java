package com.triple.mileage.mileage;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.place.Place;
import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mileage")
public class MileageManagementController {

	private final MileageManagementService mileageManagementService;

	@PostMapping
	public ResponseEntity<?> appendMileageLog(@RequestBody List<ReviewOutbox> outboxes) {
		for (ReviewOutbox outbox : outboxes) {
			mileageManagementService.handle(outbox);
		}
		return ResponseEntity.ok().build();
	}
}
