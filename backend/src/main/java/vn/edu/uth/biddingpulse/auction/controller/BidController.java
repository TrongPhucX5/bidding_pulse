package vn.edu.uth.biddingpulse.auction.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.dto.PlaceBidRequest;
import vn.edu.uth.biddingpulse.auction.service.BidService;
import vn.edu.uth.biddingpulse.model.BidHistory;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/auctions")
public class BidController {

	private final BidService bidService;

	@PostMapping("/{sessionId}/bids")
	public ResponseEntity<BigDecimal> placeBid(@PathVariable Long sessionId,
			@Valid @RequestBody PlaceBidRequest request, Authentication authentication) {
		BidHistory bid = bidService.placeBid(sessionId, authentication.getName(), request.getBidAmount());
		return ResponseEntity.status(HttpStatus.CREATED).body(bid.getBidAmount());
	}
}
