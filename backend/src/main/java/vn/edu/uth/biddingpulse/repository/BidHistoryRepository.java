package vn.edu.uth.biddingpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.uth.biddingpulse.model.BidHistory;

public interface BidHistoryRepository extends JpaRepository<BidHistory, Long> {
}
