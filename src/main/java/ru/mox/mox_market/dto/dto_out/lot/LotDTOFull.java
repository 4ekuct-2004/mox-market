package ru.mox.mox_market.dto.dto_out.lot;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.entity.tradeEnt.Lot;

@Jacksonized
@Builder
public record LotDTOFull(
        LotDTOStatic lotStaticData,
        LotDTODynamic lotDynamicData
) {
    public static LotDTOFull of(Lot lot){
        return LotDTOFull.builder()
                .lotStaticData(LotDTOStatic.of(lot))
                .lotDynamicData(LotDTODynamic.of(lot))
                .build();
    }
}
