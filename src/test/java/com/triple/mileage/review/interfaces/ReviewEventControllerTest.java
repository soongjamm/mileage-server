package com.triple.mileage.review.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.mileage.review.application.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReviewEventControllerTest {

	@Mock private ReviewService reviewService;
	private MockMvc mvc;
	private ObjectMapper objectMapper;

	@BeforeEach
	void setup() {
		objectMapper = new ObjectMapper();
		mvc = MockMvcBuilders.standaloneSetup(new ReviewEventController(reviewService))
				.addFilters(new CharacterEncodingFilter("UTF-8", true))
				.alwaysDo(print())
				.build();
	}

	@Test
	void if_review_registered_event_return_201_created_and_review_object() throws Exception {
		//given
		ReviewedEvent reviewedEvent = getReviewedEvent().action(ReviewedEvent.Action.ADD).build();
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewedEvent)));
		//then
		perform
				.andExpect(status().isCreated())
				.andExpect(redirectedUrl("/reviews/" + reviewedEvent.getReviewId()))
				.andExpect(content().string(containsString("created"))); // TODO 리뷰 객체
	}

	@Test
	void if_review_modified_return_200_ok_and_modified_review_object() throws Exception {
		//given
		ReviewedEvent reviewedEvent = getReviewedEvent().action(ReviewedEvent.Action.MOD).build();
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewedEvent)));
		//then
		perform
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("modified"))); // TODO 리뷰 객체
	}

	@Test
	void if_review_modified_return_204_no_content() throws Exception {
		//given
		ReviewedEvent reviewedEvent = getReviewedEvent().action(ReviewedEvent.Action.DELETE).build();
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewedEvent)));
		//then
		perform
				.andExpect(status().isNoContent());
	}

	@Test
	void if_request_body_is_wrong_return_400_bad_request() throws Exception {
		//given
		ReviewedEvent reviewedEvent = getReviewedEvent().action(ReviewedEvent.Action.MOD).build();
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewedEvent).replace("REVIEW", "NOT REVIEW")));
		//then
		perform
				.andExpect(status().isBadRequest());
	}

	@Test
	void if_throw_illegal_argument_exception_return_404_not_found() throws Exception {
		//given
		ReviewedEvent reviewedEvent = getReviewedEvent().action(ReviewedEvent.Action.ADD).build();
		given(reviewService.postReview(reviewedEvent)).willThrow(IllegalArgumentException.class);
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewedEvent)));
		//then
		perform
				.andExpect(status().isNotFound());
	}

	@Test
	void if_throw_illegal_state_exception_return_409_conflict() throws Exception {
		//given
		ReviewedEvent reviewedEvent = getReviewedEvent().action(ReviewedEvent.Action.ADD).build();
		given(reviewService.postReview(reviewedEvent)).willThrow(IllegalStateException.class);
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewedEvent)));
		//then
		perform
				.andExpect(status().isConflict());
	}

	private ReviewedEvent.ReviewedEventBuilder getReviewedEvent() {
		return ReviewedEvent.builder()
				.type(ReviewedEvent.Type.REVIEW)
				.action(null)
				.reviewId(UUID.fromString("24a06458-dc5f-4878-9381-ebb7b2667772"))
				.content("좋아요!")
				.attachedPhotoIds(List.of(UUID.fromString("27b02aeb-e451-44fe-95f2-d622c8798154"), UUID.fromString("f2dd5888-f6ff-4c4b-b61b-d091c1e0c69f")))
				.userId(UUID.fromString("14012214-226d-4667-8410-c5556959e139"))
				.placeId(UUID.fromString("802af381-f997-45ff-8008-1e68584eeb13"));
	}

}
