package vn.edu.uth.biddingpulse.auction.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequest {

	@NotBlank
	private String name;

	@NotBlank
	private String province;

	@NotBlank
	private String type;
}
