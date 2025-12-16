package ru.mox.mox_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mox.mox_market.entity.tradeEnt.Item;

import java.util.List;

public interface ItemRepo extends JpaRepository<Item, Long> {
}
