package ru.mox.mox_market.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.mox.mox_market.dto.ItemDTO;
import ru.mox.mox_market.dto.dto_out.lot.LotCardDTO;
import ru.mox.mox_market.dto.dto_out.lot.LotDTODynamic;
import ru.mox.mox_market.dto.dto_out.lot.LotDTOStatic;
import ru.mox.mox_market.entity.requestEnt.BuyRequest;
import ru.mox.mox_market.entity.requestEnt.SellRequest;
import ru.mox.mox_market.entity.tradeEnt.Item;
import ru.mox.mox_market.entity.tradeEnt.Lot;
import ru.mox.mox_market.entity.tradeEnt.Transaction;
import ru.mox.mox_market.repository.ItemRepo;
import ru.mox.mox_market.repository.LotRepo;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class LotService {

    private final LotRepo lotRepo;
    private final ItemRepo itemRepo;

    private final TransactionService transactionService;
    private final TradeRequestService tradeRequestService;

    public LotService(LotRepo lotRepo, ItemRepo itemRepo, TransactionService transactionService, TradeRequestService tradeRequestService) {
        this.lotRepo = lotRepo;
        this.itemRepo = itemRepo;
        this.transactionService = transactionService;
        this.tradeRequestService = tradeRequestService;
    }

    public void createNewItemAndLot(ItemDTO newItem) {
        Item item = Item.create(
                newItem.title(),
                newItem.description(),
                newItem.imageUrl()
        );
        Lot lot = Lot.create(
                item,
                new ArrayList<>(),
                new ArrayList<>(),
                new ArrayList<>(),
                false
        );
        itemRepo.save(item);
        lotRepo.save(lot);
    }

    @Cacheable(
            cacheNames = "lotCards",
            key = "'p=' + #pageable.pageNumber + ':s=' + #pageable.pageSize + ':sort=' + #pageable.sort"
    )
    public Page<LotCardDTO> getTop(Pageable pageable) {
        return lotRepo.findTop(LocalDateTime.now().minusDays(1), pageable);
    }

    @Cacheable( cacheNames = "lotsStatic", key="#lot.id")
    public LotDTOStatic getLotDTOStatic(Lot lot) {
        return LotDTOStatic.of(lot, getLotRecentTransactionsCount(lot));
    }
    @Cacheable( cacheNames = "lotsDynamic", key="#lot.id" )
    public LotDTODynamic getLotDTODynamic(Lot lot) {
        return LotDTODynamic.of(lot, getLotRecentTransactionsCount(lot));
    }

    public Integer getLotRecentTransactionsCount(Lot lot) {
        return lotRepo.countOfRecentTransactionsOfLot(LocalDateTime.now().minusDays(1), lot.getId());
    }

    public void checkRequestsIntersections(Lot lot){
        BuyRequest buy = tradeRequestService.getTopBuyRequestForLot(lot);
        SellRequest sell = tradeRequestService.getTopSellRequestForLot(lot);
        if(buy == null || sell == null) throw new IllegalArgumentException("LotService.hasRequestsIntersections | Lot does not contain request");

        if(buy.getPrice().compareTo(sell.getPrice()) > 0) prepareTransaction(lot, sell, buy);
    }

    private void prepareTransaction(Lot lot, SellRequest sell, BuyRequest buy) {
        Transaction transaction = Transaction.create(
                sell,
                buy,
                sell.getItem(),
                sell.getPrice(),
                lot
        );
        transactionService.applyTransaction(transaction);
    }

    public Lot getLotById(Long id) {
        return lotRepo.findById(id).orElse(null);
    }

}
