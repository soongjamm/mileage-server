package com.triple.mileage.mileage;

import com.triple.mileage.common.ReviewOutbox;
import com.triple.mileage.review.domain.Review;
import com.triple.mileage.review.domain.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MileageManagementService {

	private final ReviewRepository reviewRepository;
	private final MileageCalculator mileageCalculator;

	@Transactional
	public void handle(ReviewOutbox outbox) {
		Review review = reviewRepository.findById(outbox.getReviewId()).orElseThrow();
		List<MileageLog> mileages = mileageCalculator.calculate(outbox);
	}
}
