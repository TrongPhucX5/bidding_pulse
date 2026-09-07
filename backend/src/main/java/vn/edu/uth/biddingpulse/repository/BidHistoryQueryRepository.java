package vn.edu.uth.biddingpulse.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.uth.biddingpulse.model.BidHistory;

public interface BidHistoryQueryRepository extends JpaRepository<BidHistory, Long> {

	List<BidHistory> findBySessionIdOrderByCreatedAtDesc(Long sessionId);

	BidHistory findTopBySessionIdOrderByBidAmountDescCreatedAtAsc(Long sessionId);
}
