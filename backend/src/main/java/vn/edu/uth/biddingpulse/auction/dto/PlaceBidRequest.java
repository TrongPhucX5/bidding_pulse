package vn.edu.uth.biddingpulse.auction.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlaceBidRequest {

	@NotNull
	@DecimalMin(value = "0.01")
	private BigDecimal bidAmount;
}
