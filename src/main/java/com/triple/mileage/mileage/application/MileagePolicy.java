package com.triple.mileage.mileage.application;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.mileage.domain.MileageLog;
import com.triple.mileage.review.domain.Review;

public interface MileagePolicy {
	MileageLog apply(Review review);

	boolean isSatisfied(ReviewOutbox reviewOutbox, Review review);

	String getReason();
}
