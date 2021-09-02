package com.triple.mileage.mileage.application.impl;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.mileage.application.MileagePolicy;
import com.triple.mileage.mileage.domain.MileageLog;
import com.triple.mileage.mileage.domain.MileageRepository;
import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.domain.Review;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class DeleteReviewPolicy implements MileagePolicy {

	private final MileageRepository mileageRepository;

	@Override
	public MileageLog apply(Review review) {
		int sum = mileageRepository.getSumByOriginReview(review.getOriginReviewId());
		return new MileageLog(-sum, review.getUserId(), review.getReviewId(), review.getOriginReviewId(), getReason());
	}

	@Override
	public boolean isSatisfied(ReviewOutbox reviewOutbox, Review review) {
		if (reviewOutbox.getAction() == ReviewAction.DELETE) {
			return true;
		}
		return false;
	}

	@Override
	public String getReason() {
		return "Review is deleted.";
	}
}
