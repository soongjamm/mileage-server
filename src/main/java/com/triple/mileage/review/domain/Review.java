package com.triple.mileage.review.domain;

import com.triple.mileage.review.common.BaseEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Entity
public class Review extends BaseEntity {

	enum ReviewStatus {
		ORIGIN, MODIFIED, DELETED;
	}

	@Id @Type(type = "uuid-char")
	@Column(name = "REVIEW_ID")
	private UUID reviewId;
	private String content;

	@ElementCollection @Type(type = "uuid-char")
	@CollectionTable(name = "ATTACHED_PHOTO", joinColumns = @JoinColumn(name = "REVIEW_ID"))
	private List<UUID> attachedPhotoIds;

	@Type(type = "uuid-char")
	private UUID userId;

	@Type(type = "uuid-char")
	private UUID placeId;

	@Type(type = "uuid-char")
	private UUID originReviewId;

	@Enumerated(EnumType.STRING)
	private ReviewStatus reviewStatus = ReviewStatus.ORIGIN;

	protected Review() {

	}

	@Builder
	public Review(UUID reviewId, String content, List<UUID> attachedPhotoIds, UUID userId, UUID placeId) {
		this.reviewId = reviewId;
		this.content = content;
		this.attachedPhotoIds = attachedPhotoIds;
		this.userId = userId;
		this.placeId = placeId;
		this.originReviewId = reviewId;
		this.reviewStatus = ReviewStatus.ORIGIN;
	}

	private Review(UUID reviewId, String content, List<UUID> attachedPhotoIds, UUID userId, UUID placeId, UUID originReviewId) {
		this(reviewId, content, attachedPhotoIds, userId, placeId);
		this.originReviewId = originReviewId;
	}

	public Review update(String content, List<UUID> attachedPhotoIds) {
		Review review = new Review(UUID.randomUUID(), content, attachedPhotoIds, this.userId, this.placeId, this.originReviewId);
		this.reviewStatus = ReviewStatus.MODIFIED;
		return review;
	}

	public void delete() {
		this.reviewStatus = ReviewStatus.DELETED;
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
