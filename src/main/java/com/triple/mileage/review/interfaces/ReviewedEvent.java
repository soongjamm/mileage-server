package com.triple.mileage.review.interfaces;

import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ReviewedEvent {
	public enum Type {
		REVIEW
	}

	public enum Action {
		ADD, MOD, DELETE
	}

	private Type type;
	private Action action;
	private UUID reviewId;
	private String content;
	private List<UUID> attachedPhotoIds;
	private UUID userId;
	private UUID placeId;

}
