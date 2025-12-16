package ru.mox.mox_market.dto.in_dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Builder
@Jacksonized
public record SellRequestInDTO(
        Long lotId,
        Long itemId,
        LocalDateTime timestamp,
        Long authorId,
        BigDecimal price
) { }
