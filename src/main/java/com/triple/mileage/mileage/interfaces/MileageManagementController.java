package com.triple.mileage.mileage.interfaces;

import com.triple.mileage.review.application.ReviewOutbox;
import com.triple.mileage.mileage.application.CheckMileageResponse;
import com.triple.mileage.mileage.application.MileageManagementService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/mileages")
public class MileageManagementController {

	private final MileageManagementService mileageManagementService;

	@PostMapping
	public ResponseEntity<?> appendMileageLog(@RequestBody List<ReviewOutbox> outboxes) {
		for (ReviewOutbox outbox : outboxes) {
			mileageManagementService.handle(outbox);
		}
		return ResponseEntity.ok().build();
	}

	@GetMapping
	public ResponseEntity<?> checkMileage(@RequestParam UUID userId,
										  @RequestParam int page,
										  @Value("${mileage.page.size}") int size) {
		Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
		CheckMileageResponse response = mileageManagementService.checkMileage(new CheckMileageTarget(userId, pageable));
		return ResponseEntity.ok().body(response);
	}
}
