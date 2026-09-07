package vn.edu.uth.biddingpulse.auction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.exception.AuctionRuleException;
import vn.edu.uth.biddingpulse.model.AuctionRegistration;
import vn.edu.uth.biddingpulse.model.AuctionSession;
import vn.edu.uth.biddingpulse.model.AuctionStatus;
import vn.edu.uth.biddingpulse.model.BidHistory;
import vn.edu.uth.biddingpulse.model.User;
import vn.edu.uth.biddingpulse.repository.AuctionRegistrationRepository;
import vn.edu.uth.biddingpulse.repository.AuctionSessionRepository;
import vn.edu.uth.biddingpulse.repository.BidHistoryQueryRepository;
import vn.edu.uth.biddingpulse.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuctionSettlementService {

	private final AuctionSessionRepository sessionRepository;
	private final AuctionRegistrationRepository registrationRepository;
	private final BidHistoryQueryRepository bidHistoryRepository;
	private final UserRepository userRepository;

	@Transactional
	public void settle(Long sessionId) {
		AuctionSession session = sessionRepository.findByIdForUpdate(sessionId)
				.orElseThrow(() -> new AuctionRuleException("Auction session does not exist"));
		if (session.getStatus() != AuctionStatus.CLOSED) {
			throw new AuctionRuleException("Only closed sessions can be settled");
		}
		if (session.isSettled()) {
			throw new AuctionRuleException("Auction session has already been settled");
		}

		BidHistory winningBid = bidHistoryRepository.findTopBySessionIdOrderByBidAmountDescCreatedAtAsc(sessionId);
		List<AuctionRegistration> registrations = registrationRepository.findBySessionId(sessionId);
		for (AuctionRegistration registration : registrations) {
			if (winningBid == null || !registration.getUser().getId().equals(winningBid.getUser().getId())) {
				refund(registration);
			}
		}

		session.setSettled(true);
		sessionRepository.save(session);
	}

	private void refund(AuctionRegistration registration) {
		if (registration.isRefunded()) {
			return;
		}
		User user = userRepository.findByIdForUpdate(registration.getUser().getId());
		user.setBalance(user.getBalance().add(registration.getDepositAmount()));
		userRepository.save(user);
		registration.setRefunded(true);
		registrationRepository.save(registration);
	}
}
