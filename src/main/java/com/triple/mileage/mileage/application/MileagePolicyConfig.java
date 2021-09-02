package com.triple.mileage.mileage.application;

import com.triple.mileage.mileage.application.impl.*;
import com.triple.mileage.mileage.domain.MileageRepository;
import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class MileagePolicyConfig {

	@Autowired
	private PlaceRepository placeRepository;

	@Autowired
	private ReviewRepository reviewRepository;

	@Autowired
	private MileageRepository mileageRepository;

	@Bean
	public List<MileagePolicy> mileagePolicies() {
		List<MileagePolicy> policies = new ArrayList<>();
		policies.add(new FirstReviewOfPlacePolicy(placeRepository));
		policies.add(new AttachedPhotoCreationPolicy(reviewRepository));
		policies.add(new AttachedPhotoDeletionPolicy(reviewRepository));
		policies.add(new ContentCreationPolicy(reviewRepository));
		policies.add(new ContentDeletionPolicy(reviewRepository));
		policies.add(new DeleteReviewPolicy(mileageRepository));
		return policies;
	}
}
