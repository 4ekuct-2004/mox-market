package ru.mox.mox_market.dto.dto_out.lot;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.dto.ItemDTO;
import ru.mox.mox_market.entity.tradeEnt.Lot;

@Builder
@Jacksonized
public record LotDTOStatic(
        Long id,
        ItemDTO item
) {
    public static LotDTOStatic of(Lot lot) {
        return LotDTOStatic.builder()
                .id(lot.getId())
                .item(ItemDTO.of(lot.getItem()))
                .build();
    }
}
