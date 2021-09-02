package com.triple.mileage.review.domain;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Collections;
import java.util.List;

import static com.triple.mileage.review.TestData.reviewRequest;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ReviewRepositoryTest {

	@Autowired
	private ReviewRepository reviewRepository;

	@PersistenceContext
	private EntityManager em;

	@Test
	void deleteAllByOriginId() {
		//given
		Review original = reviewRequest().build().toEntity();
		Review modified = original.update("modified content", Collections.emptyList());
		reviewRepository.saveAllAndFlush(List.of(original, modified));
		em.clear();

		//when
		reviewRepository.deleteAllByOriginId(modified.getOriginReviewId());
		int size = reviewRepository.findAll().size();

		//then
		assertThat(size).isEqualTo(0);

	}

	@Test
	void save() {
		//given
		Review original = reviewRequest().build().toEntity();
		Review modified = original.update("modified content", Collections.emptyList());
		//when
		reviewRepository.saveAllAndFlush(List.of(original, modified));
		em.clear();
		//then
		assertThat(reviewRepository.findAll().size()).isEqualTo(2);
		em.clear();
	}
}