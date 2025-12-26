package ru.mox.mox_market.dto.dto_out;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.entity.requestEnt.BuyRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Jacksonized
public record BuyRequestOutDTO(
        LocalDateTime timestamp,
        BigDecimal price
) {
    public static BuyRequestOutDTO of(BuyRequest tradeRequest) {
        return BuyRequestOutDTO.builder()
                .timestamp(tradeRequest.getCreatedAt())
                .price(tradeRequest.getPrice())
                .build();
    }
}
