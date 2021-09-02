package com.triple.mileage.review.interfaces;

import com.triple.mileage.review.application.ReviewAction;
import com.triple.mileage.review.domain.Review;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReviewRequest {
	public enum Type {
		REVIEW;
	}

	private Type type;
	private ReviewAction action;
	private UUID reviewId;
	private String content;
	private List<UUID> attachedPhotoIds;
	private UUID userId;
	private UUID placeId;

	public Review toEntity() {
		return new Review(this.reviewId, this.content, this.attachedPhotoIds, this.userId, this.placeId);
	}

	public boolean isContentCreated(Review review) {
		return !this.content.isEmpty() && review.getContent().isEmpty();
	}

	public boolean isContentDeleted(Review review) {
		return this.content.isEmpty() && !review.getContent().isEmpty();
	}

	public boolean isPhotosCreated(Review review) {
		return !this.attachedPhotoIds.isEmpty() && review.getAttachedPhotoIds().isEmpty();
	}

	public boolean isPhotosDeleted(Review review) {
		return this.attachedPhotoIds.isEmpty() && !review.getAttachedPhotoIds().isEmpty();
	}
}
