package com.triple.mileage.mileage.application;

import com.triple.mileage.review.application.ReviewOutbox;
import com.triple.mileage.mileage.domain.MileageLog;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MileageCalculator {

	private final List<MileagePolicy> policies;
	private final ReviewRepository reviewRepository;

	public List<MileageLog> calculate(ReviewOutbox outbox) {
		List<MileageLog> mileages = new ArrayList<>();
		Review review = reviewRepository.findById(outbox.getReviewId()).orElseThrow();
		for (MileagePolicy policy : policies) {
			if (policy.isSatisfied(outbox, review)) {
				mileages.add(policy.apply(review));
			}
		}
		return mileages;
	}
}
