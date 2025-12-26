package ru.mox.mox_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mox.mox_market.entity.requestEnt.BuyRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface BuyRequestRepo extends JpaRepository<BuyRequest, Integer> {
    Optional<BuyRequest> findTopByTargetIdOrderByPriceDesc(Long targetId);

    List<BuyRequest> findByTargetIdOrderByPriceDesc(Long targetId);

    Integer countByTargetId(Long targetId);
}
