package vn.edu.uth.biddingpulse.auction.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.dto.AuctionSessionRequest;
import vn.edu.uth.biddingpulse.auction.exception.AuctionRuleException;
import vn.edu.uth.biddingpulse.model.AuctionSession;
import vn.edu.uth.biddingpulse.model.AuctionStatus;
import vn.edu.uth.biddingpulse.model.Item;
import vn.edu.uth.biddingpulse.repository.AuctionSessionRepository;
import vn.edu.uth.biddingpulse.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class AuctionSessionService {

	private final AuctionSessionRepository sessionRepository;
	private final ItemRepository itemRepository;

	@Transactional
	public AuctionSession create(AuctionSessionRequest request) {
		validateTime(request);
		Item item = itemRepository.findById(request.getItemId())
				.orElseThrow(() -> new AuctionRuleException("Item does not exist"));
		AuctionSession session = new AuctionSession();
		session.setItem(item);
		session.setStatus(AuctionStatus.UPCOMING);
		apply(session, request);
		session.setCurrentHighestPrice(request.getBasePrice());
		return sessionRepository.save(session);
	}

	@Transactional(readOnly = true)
	public List<AuctionSession> findAll() {
		return sessionRepository.findAll();
	}

	@Transactional(readOnly = true)
	public AuctionSession findById(Long id) {
		return sessionRepository.findById(id)
				.orElseThrow(() -> new AuctionRuleException("Auction session does not exist"));
	}

	@Transactional
	public AuctionSession update(Long id, AuctionSessionRequest request) {
		validateTime(request);
		AuctionSession session = findById(id);
		if (session.getStatus() != AuctionStatus.UPCOMING) {
			throw new AuctionRuleException("Only upcoming sessions can be updated");
		}
		Item item = itemRepository.findById(request.getItemId())
				.orElseThrow(() -> new AuctionRuleException("Item does not exist"));
		session.setItem(item);
		apply(session, request);
		session.setCurrentHighestPrice(request.getBasePrice());
		return sessionRepository.save(session);
	}

	@Transactional
	public AuctionSession open(Long id) {
		AuctionSession session = findById(id);
		if (session.getStatus() != AuctionStatus.UPCOMING || !LocalDateTime.now().isBefore(session.getEndTime())) {
			throw new AuctionRuleException("Auction session cannot be opened");
		}
		session.setStatus(AuctionStatus.OPEN);
		return sessionRepository.save(session);
	}

	@Transactional
	public AuctionSession close(Long id) {
		AuctionSession session = findById(id);
		if (session.getStatus() != AuctionStatus.OPEN) {
			throw new AuctionRuleException("Only open sessions can be closed");
		}
		session.setStatus(AuctionStatus.CLOSED);
		return sessionRepository.save(session);
	}

	private void apply(AuctionSession session, AuctionSessionRequest request) {
		session.setStartTime(request.getStartTime());
		session.setEndTime(request.getEndTime());
		session.setBasePrice(request.getBasePrice());
		session.setStepPrice(request.getStepPrice());
		session.setDepositAmount(request.getDepositAmount());
	}

	private void validateTime(AuctionSessionRequest request) {
		if (!request.getEndTime().isAfter(request.getStartTime())) {
			throw new AuctionRuleException("End time must be after start time");
		}
	}
}
