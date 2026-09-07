package vn.edu.uth.biddingpulse.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "auction_sessions")
public class AuctionSession {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "item_id", nullable = false)
	private Item item;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private BigDecimal basePrice;

	private BigDecimal stepPrice;

	private BigDecimal depositAmount;

	private BigDecimal currentHighestPrice;

	@Enumerated(EnumType.STRING)
	private AuctionStatus status;

	private boolean settled;
}
