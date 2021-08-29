package com.triple.mileage.review.interfaces;

import com.triple.mileage.review.application.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequiredArgsConstructor
@RestController
public class ReviewEventController {

	private final ReviewService reviewService;

	@PostMapping("/events")
	public ResponseEntity<?> receive(@RequestBody ReviewedEvent event) {
		try {
			switch (event.getAction()) {
				case ADD:
					reviewService.postReview(event);
					return ResponseEntity.created(URI.create("/reviews/" + event.getReviewId())).body("created");
				case MOD:
					reviewService.modifyReview(event);
					return ResponseEntity.ok("modified");
				case DELETE:
					reviewService.deleteReview(event);
					return ResponseEntity.noContent().build();
			}
		} catch (IllegalArgumentException exception) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
		} catch (IllegalStateException exception) {
			return ResponseEntity.status(HttpStatus.CONFLICT).build();
		}
		return ResponseEntity.badRequest().build();
	}

}
