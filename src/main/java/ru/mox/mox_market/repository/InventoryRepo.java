package ru.mox.mox_market.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mox.mox_market.entity.tradeEnt.Inventory;

import java.math.BigDecimal;
import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findByOwnerId(Long id);

    @Query("""
        select sum(t.price) from Transaction t
            where t.buyerRequest.author.id = :userId or t.sellerRequest.author.id = :userId
    """)
    BigDecimal getInventoryMonetaryVolumeByOwnerId(@Param("userId") Long userId);

}
