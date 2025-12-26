package ru.mox.mox_market.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.mox.mox_market.dto.dto_out.lot.LotCardDTO;
import ru.mox.mox_market.entity.tradeEnt.Lot;

import java.time.LocalDateTime;
import java.util.List;

public interface LotRepo extends JpaRepository<Lot, Long> {

    List<Lot> findByItemTitleStartingWith(String title, Pageable pageable);

    @Query("""
        select new ru.mox.mox_market.dto.dto_out.lot.LotCardDTO(
            l.id,
            new ru.mox.mox_market.dto.ItemDTO(l.item.title, l.item.description, l.item.imageUrl),
            count(l.buyRequests),
            count(l.sellRequests),
            (
                select br from BuyRequest br
                    where br.target = l
                        and br.price = (
                            select max(br2.price) from BuyRequest br2
                                where br2.target = l
                            )
                ),
            (
                select sr from SellRequest sr
                    where sr.target = l
                        and sr.price = (
                            select max(sr2.price) from SellRequest sr2
                                where sr2.target = l
                            )
                )
            
        ) from Lot l
        left join l.transactionHistory th on th.createdAt >= :from
         group by l.id
         order by count(th) desc
    """)
    Page<LotCardDTO> findTop(@Param("from") LocalDateTime from, Pageable pageable);

    @Query("""
        select count(th) from Lot l 
            left join l.transactionHistory th 
                on th.createdAt >= :from 
                    where l.id = :lotId
    """)
    Integer countOfRecentTransactionsOfLot(@Param("from") LocalDateTime from, @Param("lotId") Long lotId);
}
