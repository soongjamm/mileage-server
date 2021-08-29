package com.triple.mileage.review.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Review {
	@Id
	private UUID reviewId;
	private String content;
	@ElementCollection
	@CollectionTable(name = "ATTACHED_PHOTO")
	private List<UUID> attachedPhotoIds;
	private UUID userId;
	private UUID placeId;

	public Review(UUID reviewId, String content, List<UUID> attachedPhotoIds, UUID userId, UUID placeId) {
		this.reviewId = reviewId;
		this.content = content;
		this.attachedPhotoIds = attachedPhotoIds;
		this.userId = userId;
		this.placeId = placeId;
	}

	public void update(String content, List<UUID> attachedPhotoIds) {
		this.content = content;
		this.attachedPhotoIds.clear();
		this.attachedPhotoIds.addAll(attachedPhotoIds);
	}
}
