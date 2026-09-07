package vn.edu.uth.biddingpulse.auction.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.exception.AuctionRuleException;
import vn.edu.uth.biddingpulse.model.AuctionSession;
import vn.edu.uth.biddingpulse.model.AuctionStatus;
import vn.edu.uth.biddingpulse.model.BidHistory;
import vn.edu.uth.biddingpulse.model.User;
import vn.edu.uth.biddingpulse.repository.AuctionRegistrationRepository;
import vn.edu.uth.biddingpulse.repository.AuctionSessionRepository;
import vn.edu.uth.biddingpulse.repository.BidHistoryRepository;
import vn.edu.uth.biddingpulse.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class BidService {

	private final UserRepository userRepository;
	private final AuctionSessionRepository auctionSessionRepository;
	private final AuctionRegistrationRepository auctionRegistrationRepository;
	private final BidHistoryRepository bidHistoryRepository;

	@Transactional
	public BidHistory placeBid(Long sessionId, String username, BigDecimal bidAmount) {
		User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new AuctionRuleException("User does not exist");
		}

		if (!auctionRegistrationRepository.existsByUserIdAndSessionIdAndRefundedFalse(user.getId(), sessionId)) {
			throw new AuctionRuleException("User must register and pay the deposit before bidding");
		}

		AuctionSession session = auctionSessionRepository.findByIdForUpdate(sessionId)
				.orElseThrow(() -> new AuctionRuleException("Auction session does not exist"));

		if (session.getStatus() != AuctionStatus.OPEN) {
			throw new AuctionRuleException("Auction session is not open");
		}
		if (session.getStepPrice() == null || session.getStepPrice().signum() <= 0) {
			throw new AuctionRuleException("Auction step price is not configured");
		}
		if (session.getEndTime() == null || !LocalDateTime.now().isBefore(session.getEndTime())) {
			throw new AuctionRuleException("Auction session has ended");
		}

		BigDecimal currentPrice = session.getCurrentHighestPrice() == null
				? session.getBasePrice()
				: session.getCurrentHighestPrice();
		BigDecimal difference = bidAmount.subtract(currentPrice);
		if (bidAmount.compareTo(currentPrice) <= 0
				|| difference.remainder(session.getStepPrice()).compareTo(BigDecimal.ZERO) != 0) {
			throw new AuctionRuleException("Bid amount must be higher by a multiple of the fixed step price");
		}

		session.setCurrentHighestPrice(bidAmount);
		auctionSessionRepository.save(session);

		BidHistory bid = new BidHistory();
		bid.setSession(session);
		bid.setUser(user);
		bid.setBidAmount(bidAmount);
		bid.setCreatedAt(LocalDateTime.now());
		return bidHistoryRepository.save(bid);
	}
}
