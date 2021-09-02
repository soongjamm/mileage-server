package com.triple.mileage.review.domain;

import com.triple.mileage.review.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review extends BaseEntity {

	@Id
	@Column(name = "REVIEW_ID")
	private UUID reviewId;
	private String content;
	@ElementCollection
	@CollectionTable(name = "ATTACHED_PHOTO", joinColumns = @JoinColumn(name = "REVIEW_ID"))
	private List<UUID> attachedPhotoIds;
	private UUID userId;
	private UUID placeId;
	private UUID originReviewId;

	public Review(UUID reviewId, String content, List<UUID> attachedPhotoIds, UUID userId, UUID placeId) {
		this.reviewId = reviewId;
		this.content = content;
		this.attachedPhotoIds = attachedPhotoIds;
		this.userId = userId;
		this.placeId = placeId;
		this.originReviewId = reviewId;
	}

	private Review(UUID reviewId, String content, List<UUID> attachedPhotoIds, UUID userId, UUID placeId, UUID originReviewId) {
		this(reviewId, content, attachedPhotoIds, userId, placeId);
		this.originReviewId = originReviewId;
	}

	public Review update(String content, List<UUID> attachedPhotoIds) {
		Review review = new Review(UUID.randomUUID(), content, attachedPhotoIds, this.userId, this.placeId, this.originReviewId);
		return review;
	}

	@Override
	public LocalDateTime getCreatedDate() {
		return super.getCreatedDate();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Review review = (Review) o;
		return reviewId.equals(review.reviewId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(reviewId);
	}
}
