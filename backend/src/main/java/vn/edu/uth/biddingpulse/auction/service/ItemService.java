package vn.edu.uth.biddingpulse.auction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import vn.edu.uth.biddingpulse.auction.dto.ItemRequest;
import vn.edu.uth.biddingpulse.auction.exception.AuctionRuleException;
import vn.edu.uth.biddingpulse.model.Item;
import vn.edu.uth.biddingpulse.repository.ItemRepository;

@Service
@RequiredArgsConstructor
public class ItemService {

	private final ItemRepository itemRepository;

	@Transactional
	public Item create(ItemRequest request) {
		Item item = new Item();
		apply(item, request);
		return itemRepository.save(item);
	}

	@Transactional(readOnly = true)
	public List<Item> findAll() {
		return itemRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Item findById(Long id) {
		return itemRepository.findById(id)
				.orElseThrow(() -> new AuctionRuleException("Item does not exist"));
	}

	@Transactional
	public Item update(Long id, ItemRequest request) {
		Item item = findById(id);
		apply(item, request);
		return itemRepository.save(item);
	}

	@Transactional
	public void delete(Long id) {
		if (!itemRepository.existsById(id)) {
			throw new AuctionRuleException("Item does not exist");
		}
		itemRepository.deleteById(id);
	}

	private void apply(Item item, ItemRequest request) {
		item.setName(request.getName());
		item.setProvince(request.getProvince());
		item.setType(request.getType());
	}
}
