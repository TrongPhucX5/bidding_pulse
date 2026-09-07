package vn.edu.uth.biddingpulse.identity.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.security.dto.LoginRequest;
import vn.edu.uth.biddingpulse.security.dto.LoginResponse;
import vn.edu.uth.biddingpulse.security.jwt.JwtTokenService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class LoginController {

	private final JwtTokenService jwtTokenService;

	@PostMapping("/login")
	@Operation(tags = "Authentication", description = "Authenticate a user and return a JWT token.")
	public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(jwtTokenService.getLoginResponse(request));
	}
}