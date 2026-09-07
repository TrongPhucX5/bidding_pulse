package vn.edu.uth.biddingpulse.security.jwt;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import vn.edu.uth.biddingpulse.common.response.ApiErrorResponse;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private final ObjectMapper objectMapper;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		response.setContentType("application/json");
		ApiErrorResponse error = new ApiErrorResponse(
				HttpServletResponse.SC_UNAUTHORIZED,
				"UNAUTHORIZED",
				"Authentication is required",
				request.getRequestURI(),
				LocalDateTime.now(),
				List.of());
		objectMapper.writeValue(response.getOutputStream(), error);
	}

}
