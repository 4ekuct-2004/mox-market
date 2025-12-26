package ru.mox.mox_market.dto.in_dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record SellRequestInDTO(
        Long lotId,
        Long itemId,
        LocalDateTime timestamp,
        Long authorId,
        BigDecimal price
) { }
