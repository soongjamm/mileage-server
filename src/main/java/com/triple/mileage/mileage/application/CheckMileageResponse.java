package com.triple.mileage.mileage.application;

import com.triple.mileage.mileage.domain.MileageLog;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CheckMileageResponse {
	private int sum;
	private Page<MileageLog> logs;
}
