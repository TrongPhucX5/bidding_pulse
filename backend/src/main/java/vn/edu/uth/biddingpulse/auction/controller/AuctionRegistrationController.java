package vn.edu.uth.biddingpulse.auction.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.service.AuctionRegistrationService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auctions")
public class AuctionRegistrationController {

	private final AuctionRegistrationService registrationService;

	@PostMapping("/{sessionId}/registrations")
	public ResponseEntity<Void> register(@PathVariable Long sessionId, Authentication authentication) {
		registrationService.register(sessionId, authentication.getName());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
