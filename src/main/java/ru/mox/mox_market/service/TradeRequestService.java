package ru.mox.mox_market.service;

import org.springframework.stereotype.Service;
import ru.mox.mox_market.entity.requestEnt.BuyRequest;
import ru.mox.mox_market.entity.requestEnt.SellRequest;
import ru.mox.mox_market.entity.tradeEnt.Lot;
import ru.mox.mox_market.repository.BuyRequestRepo;
import ru.mox.mox_market.repository.SellRequestRepo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class TradeRequestService {

    public static BigDecimal MAX_PRICE = new BigDecimal("1000000.00");
    public static final BigDecimal MIN_PRICE = new BigDecimal("0.10");
    public static final BigDecimal COMMISSION_MODIFIER = new BigDecimal("1.15");

    private final SellRequestRepo sellRequestRepo;
    private final BuyRequestRepo buyRequestRepo;

    private final LotService lotService;
    private final InventoryService inventoryService;

    public TradeRequestService(SellRequestRepo sellRequestRepo, BuyRequestRepo buyRequestRepo, LotService lotService, InventoryService inventoryService) {
        this.sellRequestRepo = sellRequestRepo;
        this.buyRequestRepo = buyRequestRepo;
        this.lotService = lotService;
        this.inventoryService = inventoryService;
    }

    public void addSellRequest(SellRequest sellRequest, Lot lot) {
        if(sellRequest.getPrice().compareTo(MAX_PRICE) > 0 && sellRequest.getPrice().compareTo(MIN_PRICE) < 0)
            throw new IllegalArgumentException("LotService.addSellRequest | Price out of limit");

        sellRequest.setPrice(sellRequest.getPrice().multiply(COMMISSION_MODIFIER).setScale(2, RoundingMode.HALF_UP));
        sellRequest.getItem().setOnSell(true);

        lot.getSellRequests().add(sellRequest);

        sellRequestRepo.save(sellRequest);

        lotService.checkRequestsIntersections(lot);
    }
    public void deleteSellRequest(SellRequest sellRequest, Lot lot) {
        if(!lot.getSellRequests().contains(sellRequest))
            throw new IllegalArgumentException("LotService.deleteSellRequest | Lot does not contain request");

        sellRequest.getItem().setOnSell(false);

        lot.getSellRequests().remove(sellRequest);

        sellRequestRepo.delete(sellRequest);
    }

    public void addBuyRequest(BuyRequest buyRequest, Lot lot) {
        if(buyRequest.getPrice().compareTo(MAX_PRICE) > 0 && buyRequest.getPrice().compareTo(MIN_PRICE) < 0)
            throw new IllegalArgumentException("LotService.addBuyRequest | Price out of limit");

        inventoryService.holdUserMoney(buyRequest.getAuthor(), buyRequest.getPrice());

        lot.getBuyRequests().add(buyRequest);
        buyRequestRepo.save(buyRequest);

        lotService.checkRequestsIntersections(lot);
    }
    public void deleteBuyRequest(BuyRequest buyRequest, Lot lot) {
        if(!lot.getBuyRequests().contains(buyRequest))
            throw new IllegalArgumentException("LotService.deleteBuyRequest | Lot does not contain request");

        inventoryService.unholdUserMoney(buyRequest.getAuthor(), buyRequest.getPrice());

        lot.getBuyRequests().remove(buyRequest);

        buyRequestRepo.delete(buyRequest);
    }

    public BuyRequest getTopBuyRequestForLot(Lot lot) {
        return buyRequestRepo.findTopByTargetIdOrderByPriceDesc(lot.getId()).orElse(null);
    }
    public SellRequest getTopSellRequestForLot(Lot lot) {
        return sellRequestRepo.findTopByTargetIdOrderByPriceAsc(lot.getId()).orElse(null);
    }

}
