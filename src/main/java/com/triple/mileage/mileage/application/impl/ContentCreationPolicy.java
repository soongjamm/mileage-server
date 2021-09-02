package com.triple.mileage.mileage.application.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.mileage.application.MileagePolicy;
import com.triple.mileage.mileage.domain.MileageLog;
import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.infra.ReviewModified;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.triple.mileage.common.Utility.baseObjectMapper;

@RequiredArgsConstructor
@Component
public class ContentCreationPolicy implements MileagePolicy {

	private final ReviewRepository reviewRepository;

	@Override
	public MileageLog apply(Review review) {
		return new MileageLog(1, review.getUserId(), review.getReviewId(), review.getOriginReviewId(), getReason());
	}

	@Override
	public boolean isSatisfied(ReviewOutbox reviewOutbox, Review review) {
		if (review.getContent().isEmpty()) {
			return false;
		}
		if (reviewOutbox.getAction() == ReviewAction.ADD) {
			return true;
		}
		if (reviewOutbox.getAction() == ReviewAction.MOD) {
			ReviewModified reviewModified = null;
			try {
				reviewModified = baseObjectMapper().readValue(reviewOutbox.getPayload(), ReviewModified.class);
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			Review original = reviewRepository.findById(reviewModified.getOriginalReviewId()).orElseThrow();
			if (original.getContent().isEmpty()) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getReason() {
		return "Content is newly included.";
	}
}
