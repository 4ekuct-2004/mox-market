package ru.mox.mox_market.entity.tradeEnt;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mox.mox_market.entity.BaseEntity;
import ru.mox.mox_market.entity.authEnt.MoxUser;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Inventory extends BaseEntity {

    @OneToOne
    private MoxUser owner;

    private BigDecimal balance;
    private BigDecimal withholdenBalance;

    private List<ItemInstance> items;

    private List<Transaction> transactionHistory;

    private boolean visibleForEveryone;

    public static Inventory of(MoxUser owner) {
        Inventory inventory = new Inventory();
        inventory.setOwner(owner);
        inventory.setItems(new ArrayList<>());
        inventory.setTransactionHistory(new ArrayList<>());
        inventory.setVisibleForEveryone(true);
        inventory.setBalance(new BigDecimal("0.00"));
        inventory.setWithholdenBalance(new BigDecimal("0.00"));

        return inventory;
    }

}
