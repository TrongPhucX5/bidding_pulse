package vn.edu.uth.biddingpulse.auction.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.dto.AuctionSessionRequest;
import vn.edu.uth.biddingpulse.auction.service.AuctionSessionService;
import vn.edu.uth.biddingpulse.model.AuctionSession;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/auction-sessions")
public class AuctionSessionController {

	private final AuctionSessionService sessionService;

	@PostMapping
	public ResponseEntity<AuctionSession> create(@Valid @RequestBody AuctionSessionRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(sessionService.create(request));
	}

	@GetMapping
	public List<AuctionSession> findAll() {
		return sessionService.findAll();
	}

	@GetMapping("/{id}")
	public AuctionSession findById(@PathVariable Long id) {
		return sessionService.findById(id);
	}

	@PutMapping("/{id}")
	public AuctionSession update(@PathVariable Long id, @Valid @RequestBody AuctionSessionRequest request) {
		return sessionService.update(id, request);
	}

	@PostMapping("/{id}/open")
	public AuctionSession open(@PathVariable Long id) {
		return sessionService.open(id);
	}

	@PostMapping("/{id}/close")
	public AuctionSession close(@PathVariable Long id) {
		return sessionService.close(id);
	}
}
