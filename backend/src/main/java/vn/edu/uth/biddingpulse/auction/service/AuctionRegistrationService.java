package vn.edu.uth.biddingpulse.auction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.exception.AuctionRuleException;
import vn.edu.uth.biddingpulse.model.AuctionRegistration;
import vn.edu.uth.biddingpulse.model.AuctionSession;
import vn.edu.uth.biddingpulse.model.AuctionStatus;
import vn.edu.uth.biddingpulse.model.User;
import vn.edu.uth.biddingpulse.repository.AuctionRegistrationRepository;
import vn.edu.uth.biddingpulse.repository.AuctionSessionRepository;
import vn.edu.uth.biddingpulse.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class AuctionRegistrationService {

	private final UserRepository userRepository;
	private final AuctionSessionRepository auctionSessionRepository;
	private final AuctionRegistrationRepository auctionRegistrationRepository;

	@Transactional
	public AuctionRegistration register(Long sessionId, String username) {
		User user = userRepository.findByUsernameForUpdate(username);
		if (user == null) {
			throw new AuctionRuleException("User does not exist");
		}

		AuctionSession session = auctionSessionRepository.findByIdForUpdate(sessionId)
				.orElseThrow(() -> new AuctionRuleException("Auction session does not exist"));
		if (session.getStatus() != AuctionStatus.UPCOMING
				|| session.getStartTime() == null
				|| !LocalDateTime.now().isBefore(session.getStartTime())) {
			throw new AuctionRuleException("Registration is closed for this auction session");
		}
		if (auctionRegistrationRepository.existsByUserIdAndSessionId(user.getId(), sessionId)) {
			throw new AuctionRuleException("User is already registered for this auction session");
		}

		BigDecimal deposit = session.getDepositAmount();
		if (deposit == null || deposit.signum() <= 0) {
			throw new AuctionRuleException("Auction deposit is not configured");
		}
		if (user.getBalance() == null || user.getBalance().compareTo(deposit) < 0) {
			throw new AuctionRuleException("Insufficient wallet balance for the deposit");
		}

		user.setBalance(user.getBalance().subtract(deposit));
		userRepository.save(user);

		AuctionRegistration registration = new AuctionRegistration();
		registration.setUser(user);
		registration.setSession(session);
		registration.setDepositAmount(deposit);
		registration.setRefunded(false);
		return auctionRegistrationRepository.save(registration);
	}
}
