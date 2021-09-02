package com.triple.mileage.mileage.interfaces;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class CheckMileageTarget {
	private UUID userId;
	private Pageable pageable;
}
