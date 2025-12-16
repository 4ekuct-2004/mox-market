package ru.mox.mox_market.dto;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.entity.tradeEnt.Item;

@Builder
@Jacksonized
public record ItemDTO(
        String title,
        String description,
        String imageUrl
) {
    public static ItemDTO of(Item item) {
        return ItemDTO.builder()
                .title(item.getTitle())
                .description(item.getDescription())
                .imageUrl(item.getImageUrl())
                .build();
    }
}
