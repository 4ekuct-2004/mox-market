package ru.mox.mox_market.dto.dto_out;

import lombok.Builder;
import lombok.extern.jackson.Jacksonized;
import ru.mox.mox_market.dto.ItemDTO;
import ru.mox.mox_market.dto.TransactionDTO;
import ru.mox.mox_market.entity.tradeEnt.Inventory;

import java.math.BigDecimal;
import java.util.List;

@Builder
@Jacksonized
public record InventoryDTO(
        BigDecimal balance,
        List<ItemDTO> items,
        List<TransactionDTO> transactionHistory,
        boolean visibleForEveryone
) {
    public static InventoryDTO of(Inventory inv) {
        return InventoryDTO.builder()
                .balance(inv.getBalance())
                .items(inv.getItems().stream().map(ItemDTO::of).toList())
                .visibleForEveryone(inv.isVisibleForEveryone())
                .build();
    }
}
