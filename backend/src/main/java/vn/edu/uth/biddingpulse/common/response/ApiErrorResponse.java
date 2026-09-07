package vn.edu.uth.biddingpulse.common.response;

import java.time.LocalDateTime;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiErrorResponse {

	private final int status;
	private final String code;
	private final String message;
	private final String path;
	private final LocalDateTime timestamp;
	private final List<String> errors;
}