package ru.mox.mox_market.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.entity.tradeEnt.Transaction;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Jacksonized
public record TransactionDTO(
        ItemDTO item,
        LocalDateTime timestamp,
        BigDecimal price
) {
    public static TransactionDTO of(Transaction transaction) {
        return TransactionDTO.builder()
                .item(ItemDTO.of(transaction.getItemInstance().getItem()))
                .timestamp(transaction.getCreatedAt())
                .price(transaction.getPrice())
                .build();
    }
}
