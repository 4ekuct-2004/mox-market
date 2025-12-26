package ru.mox.mox_market.dto.dto_out;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.entity.requestEnt.SellRequest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Jacksonized
public record SellRequestOutDTO(
        LocalDateTime timestamp,
        BigDecimal price
) {
    public static SellRequestOutDTO of(SellRequest tradeRequest) {
        return SellRequestOutDTO.builder()
                .timestamp(tradeRequest.getCreatedAt())
                .price(tradeRequest.getPrice())
                .build();
    }
}
