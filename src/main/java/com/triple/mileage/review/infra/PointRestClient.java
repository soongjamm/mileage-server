package com.triple.mileage.review.infra;

import com.triple.mileage.common.ReviewOutbox;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PointRestClient {

	@Value("${url.point.append}")
	private String url;
	private RestTemplate restTemplate = new RestTemplate();

	public PointRestClient() {
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		converter.setSupportedMediaTypes(Collections.singletonList(MediaType.APPLICATION_JSON));
		messageConverters.add(converter);
		restTemplate.setMessageConverters(messageConverters);
	}

	@Retryable(value = RuntimeException.class, maxAttempts = 3, backoff = @Backoff(delay = 100))
	public ResponseEntity<Void> addPoint(List<ReviewOutbox> outboxes) {
		return restTemplate.postForEntity(url, outboxes, Void.class);
	}
}
