package vn.edu.uth.biddingpulse.identity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.security.dto.RegistrationRequest;
import vn.edu.uth.biddingpulse.security.dto.RegistrationResponse;
import vn.edu.uth.biddingpulse.security.service.UserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class RegistrationController {

	private final UserService userService;

	@PostMapping("/register")
	@Operation(tags = "Authentication", description = "Register a new user.")
	public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(userService.registration(request));
	}
}