package vn.edu.uth.biddingpulse.auction.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.service.AuctionSettlementService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction-sessions")
public class AuctionSettlementController {

	private final AuctionSettlementService settlementService;

	@PostMapping("/{id}/settle")
	public ResponseEntity<Void> settle(@PathVariable Long id) {
		settlementService.settle(id);
		return ResponseEntity.noContent().build();
	}
}
