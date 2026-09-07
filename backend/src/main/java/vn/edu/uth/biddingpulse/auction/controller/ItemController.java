package vn.edu.uth.biddingpulse.auction.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.dto.ItemRequest;
import vn.edu.uth.biddingpulse.auction.service.ItemService;
import vn.edu.uth.biddingpulse.model.Item;

@RestController
@RequiredArgsConstructor
@Validated
@RequestMapping("/api/v1/items")
public class ItemController {

	private final ItemService itemService;

	@PostMapping
	public ResponseEntity<Item> create(@Valid @RequestBody ItemRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(itemService.create(request));
	}

	@GetMapping
	public List<Item> findAll() {
		return itemService.findAll();
	}

	@GetMapping("/{id}")
	public Item findById(@PathVariable Long id) {
		return itemService.findById(id);
	}

	@PutMapping("/{id}")
	public Item update(@PathVariable Long id, @Valid @RequestBody ItemRequest request) {
		return itemService.update(id, request);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		itemService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
