package ru.mox.mox_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mox.mox_market.entity.tradeEnt.ItemInstance;

public interface ItemInstanceRepo extends JpaRepository<ItemInstance, Long> {
}
