package com.triple.mileage.review;

import com.triple.mileage.review.interfaces.ReviewRequest;

import java.util.List;
import java.util.UUID;

public class TestData {
	public static ReviewRequest.ReviewRequestBuilder reviewRequest() {
		return ReviewRequest.builder()
				.type(ReviewRequest.Type.REVIEW)
				.action(null)
				.reviewId(UUID.fromString("24a06458-dc5f-4878-9381-ebb7b2667772"))
				.content("좋아요!")
				.attachedPhotoIds(List.of(UUID.fromString("27b02aeb-e451-44fe-95f2-d622c8798154"), UUID.fromString("f2dd5888-f6ff-4c4b-b61b-d091c1e0c69f")))
				.userId(UUID.fromString("14012214-226d-4667-8410-c5556959e139"))
				.placeId(UUID.fromString("802af381-f997-45ff-8008-1e68584eeb13"));
	}

}
