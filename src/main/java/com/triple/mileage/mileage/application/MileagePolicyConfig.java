package com.triple.mileage.mileage.application;

import com.triple.mileage.mileage.application.impl.*;
import com.triple.mileage.mileage.domain.MileageRepository;
import com.triple.mileage.place.PlaceRepository;
import com.triple.mileage.review.domain.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;

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
		return List.of(
				new FirstReviewOfPlacePolicy(placeRepository),
				new AttachedPhotoCreationPolicy(reviewRepository),
				new AttachedPhotoDeletionPolicy(reviewRepository),
				new ContentCreationPolicy(reviewRepository),
				new ContentDeletionPolicy(reviewRepository),
				new DeleteReviewPolicy(mileageRepository));
	}
}
