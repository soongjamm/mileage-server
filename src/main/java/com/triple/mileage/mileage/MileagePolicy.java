package com.triple.mileage.mileage;

import com.triple.mileage.common.ReviewOutbox;

public interface MileagePolicy {
	MileageLog apply(ReviewOutbox reviewOutbox);

	boolean isSatisfied(ReviewOutbox reviewOutbox);

	String getReason();
}
