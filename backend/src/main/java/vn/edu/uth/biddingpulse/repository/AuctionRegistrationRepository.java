package vn.edu.uth.biddingpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

import vn.edu.uth.biddingpulse.model.AuctionRegistration;

public interface AuctionRegistrationRepository extends JpaRepository<AuctionRegistration, Long> {

	boolean existsByUserIdAndSessionId(Long userId, Long sessionId);

	boolean existsByUserIdAndSessionIdAndRefundedFalse(Long userId, Long sessionId);

	List<AuctionRegistration> findBySessionId(Long sessionId);
}
