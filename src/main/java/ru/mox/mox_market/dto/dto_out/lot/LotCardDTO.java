package ru.mox.mox_market.dto.dto_out.lot;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.dto.ItemDTO;
import ru.mox.mox_market.dto.dto_out.BuyRequestOutDTO;
import ru.mox.mox_market.dto.dto_out.SellRequestOutDTO;
import ru.mox.mox_market.entity.requestEnt.BuyRequest;
import ru.mox.mox_market.entity.requestEnt.SellRequest;
import ru.mox.mox_market.entity.tradeEnt.Lot;

import java.util.Comparator;

@Builder
@Jacksonized
public record LotCardDTO (
    Long id,
    ItemDTO item,
    Integer buyCount,
    Integer sellCount,
    BuyRequestOutDTO bestBuy,
    SellRequestOutDTO bestSell
) {
    public static LotCardDTO of(Lot lot){
        return LotCardDTO.builder()
                .id(lot.getId())
                .item(ItemDTO.of(lot.getItem()))
                .buyCount(lot.getBuyRequests().size())
                .sellCount(lot.getSellRequests().size())
                .bestBuy(BuyRequestOutDTO.of(lot.getBuyRequests().stream().max(Comparator.comparing(BuyRequest::getPrice)).orElse(null)))
                .bestSell(SellRequestOutDTO.of(lot.getSellRequests().stream().min(Comparator.comparing(SellRequest::getPrice)).orElse(null)))
                .build();
    }
}
