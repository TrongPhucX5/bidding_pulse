package vn.edu.uth.biddingpulse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import vn.edu.uth.biddingpulse.model.Item;

public interface ItemRepository extends JpaRepository<Item, Long> {
}
