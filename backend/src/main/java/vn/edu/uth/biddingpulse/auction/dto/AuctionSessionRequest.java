package vn.edu.uth.biddingpulse.auction.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuctionSessionRequest {

	@NotNull
	private Long itemId;

	@NotNull
	@Future
	private LocalDateTime startTime;

	@NotNull
	@Future
	private LocalDateTime endTime;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal basePrice;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal stepPrice;

	@NotNull
	@DecimalMin("0.01")
	private BigDecimal depositAmount;
}
