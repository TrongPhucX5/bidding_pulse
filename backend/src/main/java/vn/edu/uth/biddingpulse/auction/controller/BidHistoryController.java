package vn.edu.uth.biddingpulse.auction.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.model.BidHistory;
import vn.edu.uth.biddingpulse.repository.BidHistoryQueryRepository;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auction-sessions")
public class BidHistoryController {

	private final BidHistoryQueryRepository bidHistoryRepository;

	@GetMapping("/{sessionId}/bids")
	public List<BidHistory> findBySession(@PathVariable Long sessionId) {
		return bidHistoryRepository.findBySessionIdOrderByCreatedAtDesc(sessionId);
	}
}
