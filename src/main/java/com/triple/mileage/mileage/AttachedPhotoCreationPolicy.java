package com.triple.mileage.mileage;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.application.ReviewModified;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AttachedPhotoCreationPolicy implements MileagePolicy {

	private final ReviewRepository reviewRepository;

	@Override
	public MileageLog apply(ReviewOutbox reviewOutbox) {
		return new MileageLog(1, reviewOutbox.getReviewId(), getReason());
	}

	@Override
	public boolean isSatisfied(ReviewOutbox reviewOutbox) {
		Review review = reviewRepository.findById(reviewOutbox.getReviewId()).orElseThrow();
		if (review.getContent().isEmpty()) {
			return false;
		}
		if (reviewOutbox.getAction() == ReviewAction.ADD) {
			return true;
		}
		if (reviewOutbox.getAction() == ReviewAction.MOD) {
			ReviewModified reviewModified = null;
			try {
				reviewModified = new ObjectMapper().readValue(reviewOutbox.getPayload(), ReviewModified.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			Review original = reviewRepository.findById(reviewModified.getOriginalReviewId()).orElseThrow();
			if (original.getAttachedPhotoIds().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getReason() {
		return "Photos are newly included.";
	}
}
