package ru.mox.mox_market.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mox.mox_market.entity.requestEnt.SellRequest;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellRequestRepo extends JpaRepository<SellRequest, Integer> {
    Optional<SellRequest> findTopByTargetIdOrderByPriceAsc(Long targetId);

    List<SellRequest> findByTargetId(Long targetId, Pageable pageable);

    Integer countByTargetId(Long targetId);
}
