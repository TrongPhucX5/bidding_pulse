package vn.edu.uth.biddingpulse.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import jakarta.persistence.LockModeType;
import vn.edu.uth.biddingpulse.model.AuctionSession;

public interface AuctionSessionRepository extends JpaRepository<AuctionSession, Long> {

	@Lock(LockModeType.PESSIMISTIC_WRITE)
	@Query("select session from AuctionSession session where session.id = :id")
	Optional<AuctionSession> findByIdForUpdate(@Param("id") Long id);
}
