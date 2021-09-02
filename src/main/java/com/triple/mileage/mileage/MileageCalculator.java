package com.triple.mileage.mileage;

import com.triple.mileage.common.ReviewOutbox;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class MileageCalculator {

	private final List<MileagePolicy> policies;

	public List<MileageLog> calculate(ReviewOutbox reviewOutbox) {
		List<MileageLog> mileages = new ArrayList<>();
		for (MileagePolicy policy : policies) {
			if (policy.isSatisfied(reviewOutbox)) {
				mileages.add(policy.apply(reviewOutbox));
			}
		}
		return mileages;
	}
}
