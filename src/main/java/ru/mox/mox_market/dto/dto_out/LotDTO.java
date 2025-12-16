package ru.mox.mox_market.dto.dto_out;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.dto.ItemDTO;
import ru.mox.mox_market.entity.tradeEnt.Lot;

import java.util.List;

@Builder
@Jacksonized
public record LotDTO(
        ItemDTO item,
        List<BuyRequestOutDTO> buyRequests,
        List<SellRequestOutDTO> sellRequests,
        boolean blocked
) {
    public static LotDTO of(Lot lot){
        return LotDTO.builder()
                .item(ItemDTO.of(lot.getItem()))
                .buyRequests(lot.getBuyRequests().stream().map(BuyRequestOutDTO::of).toList())
                .sellRequests(lot.getSellRequests().stream().map(SellRequestOutDTO::of).toList())
                .blocked(lot.isBlocked())
                .build();
    }
}
