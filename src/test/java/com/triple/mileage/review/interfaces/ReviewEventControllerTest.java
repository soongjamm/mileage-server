package com.triple.mileage.review.interfaces;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.triple.mileage.review.application.ReviewAction;
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

import static com.triple.mileage.review.TestData.reviewRequest;
import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class ReviewEventControllerTest {

	@Mock
	private ReviewService reviewService;
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
		ReviewRequest reviewRequest = reviewRequest().action(ReviewAction.ADD).build();
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewRequest)));
		//then
		perform
				.andExpect(status().isCreated())
				.andExpect(redirectedUrl("/reviews/" + reviewRequest.getReviewId()))
				.andExpect(content().string(containsString("created"))); // TODO ?????? ??????
	}

	@Test
	void if_review_modified_return_200_ok_and_modified_review_object() throws Exception {
		//given
		ReviewRequest reviewRequest = reviewRequest().action(ReviewAction.MOD).build();
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewRequest)));
		//then
		perform
				.andExpect(status().isOk())
				.andExpect(content().string(containsString("modified"))); // TODO ?????? ??????
	}

	@Test
	void if_review_modified_return_204_no_content() throws Exception {
		//given
		ReviewRequest reviewRequest = reviewRequest().action(ReviewAction.DELETE).build();
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewRequest)));
		//then
		perform
				.andExpect(status().isNoContent());
	}

	@Test
	void if_request_body_is_wrong_return_400_bad_request() throws Exception {
		//given
		ReviewRequest reviewRequest = reviewRequest().action(ReviewAction.MOD).build();
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewRequest).replace("REVIEW", "NOT REVIEW")));
		//then
		perform
				.andExpect(status().isBadRequest());
	}

	@Test
	void if_throw_illegal_argument_exception_return_404_not_found() throws Exception {
		//given
		ReviewRequest reviewRequest = reviewRequest().action(ReviewAction.ADD).build();
		given(reviewService.addReview(reviewRequest)).willThrow(IllegalArgumentException.class);
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewRequest)));
		//then
		perform
				.andExpect(status().isNotFound());
	}

	@Test
	void if_throw_illegal_state_exception_return_409_conflict() throws Exception {
		//given
		ReviewRequest reviewRequest = reviewRequest().action(ReviewAction.ADD).build();
		given(reviewService.addReview(reviewRequest)).willThrow(IllegalStateException.class);
		//when
		ResultActions perform = mvc.perform(post("/events")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(reviewRequest)));
		//then
		perform
				.andExpect(status().isConflict());
	}

}
