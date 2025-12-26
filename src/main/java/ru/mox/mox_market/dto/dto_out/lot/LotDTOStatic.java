package ru.mox.mox_market.dto.dto_out.lot;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.dto.ItemDTO;
import ru.mox.mox_market.dto.dto_out.BuyRequestOutDTO;
import ru.mox.mox_market.dto.dto_out.SellRequestOutDTO;
import ru.mox.mox_market.entity.tradeEnt.Lot;
import ru.mox.mox_market.entity.tradeEnt.Transaction;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Jacksonized
public record LotDTOStatic(
        Long id,
        ItemDTO item,
        boolean blocked
) {
    public static LotDTOStatic of(Lot lot, Integer recentTransactionsCount) {
        return LotDTOStatic.builder()
                .id(lot.getId())
                .item(ItemDTO.of(lot.getItem()))
                .blocked(lot.isBlocked())
                .build();
    }
}
