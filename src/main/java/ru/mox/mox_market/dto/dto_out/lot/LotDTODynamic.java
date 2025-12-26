package ru.mox.mox_market.dto.dto_out.lot;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.dto.dto_out.BuyRequestOutDTO;
import ru.mox.mox_market.dto.dto_out.SellRequestOutDTO;
import ru.mox.mox_market.entity.tradeEnt.Lot;
import ru.mox.mox_market.entity.tradeEnt.Transaction;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Jacksonized
public record LotDTODynamic(
        Long id,
        List<BuyRequestOutDTO> buyRequests,
        List<SellRequestOutDTO> sellRequests,
        Integer recentTransactionsCount,
        List<BigDecimal> priceHistory
) {
    public static LotDTODynamic of(Lot lot, Integer recentTransactionsCount) {
        return LotDTODynamic.builder()
                .id(lot.getId())
                .buyRequests(lot.getBuyRequests().stream().map(BuyRequestOutDTO::of).toList())
                .sellRequests(lot.getSellRequests().stream().map(SellRequestOutDTO::of).toList())
                .recentTransactionsCount(recentTransactionsCount)
                .priceHistory(lot.getTransactionHistory().stream().map(Transaction::getPrice).toList())
                .build();
    }
}
