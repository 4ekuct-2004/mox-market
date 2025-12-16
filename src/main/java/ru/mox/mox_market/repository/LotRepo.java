package ru.mox.mox_market.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mox.mox_market.entity.tradeEnt.Lot;

import java.util.List;

public interface LotRepo extends JpaRepository<Lot, Long> {

    List<Lot> findByItemTitleStartingWith(String title, Pageable pageable);

}
