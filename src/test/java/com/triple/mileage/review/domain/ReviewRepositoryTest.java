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
	void save() {
		//given
		Review original = reviewRequest().build().toEntity();
		Review modified = original.update("modified content", Collections.emptyList());
		//when
		reviewRepository.saveAllAndFlush(List.of(original, modified));
		List<Review> all = reviewRepository.findAll();
		em.clear();
		//then
		assertThat(all.size()).isEqualTo(2);
		System.out.println(all.get(0).getCreatedDate());
		em.clear();
	}
}