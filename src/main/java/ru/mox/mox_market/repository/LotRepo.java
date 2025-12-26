package ru.mox.mox_market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mox.mox_market.dto.dto_out.lot.LotDTOStatic;
import ru.mox.mox_market.entity.tradeEnt.Lot;

import java.time.LocalDateTime;
import java.util.List;

public interface LotRepo extends JpaRepository<Lot, Long> {

    List<Lot> findByItemTitleStartingWith(String title, Pageable pageable);

    @Query("""
        select new ru.mox.mox_market.dto.dto_out.LotDTO(
            l.id,
            new ru.mox.mox_market.dto.ItemDTO(l.item.title, l.item.description, l.item.imageUrl),
            l.buyRequests,
            l.sellRequests,
            count(th),
            count(l.transactionHistory),
            l.blocked
        ) from Lot l
        left join l.transactionHistory th
            on th.createdAt >= :from
         group by l.id, l.item, l.buyRequests, l.sellRequests, l.blocked
         order by count(th) desc
    """)
    Page<LotDTOStatic> findTop(@Param("from") LocalDateTime from, Pageable pageable);

    @Query("select count(th) from Lot l left join l.transactionHistory th on th.createdAt >= :from where l.id = :lotId")
    Integer countOfRecentTransactionsOfLot(@Param("from") LocalDateTime from, @Param("lotId") Long lotId);
}
