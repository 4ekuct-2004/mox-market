package ru.mox.mox_market.entity.tradeEnt;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mox.mox_market.entity.BaseEntity;
import ru.mox.mox_market.entity.requestEnt.BuyRequest;
import ru.mox.mox_market.entity.requestEnt.SellRequest;

import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
public class Lot extends BaseEntity {

    @OneToOne
    private Item item;

    private List<SellRequest> sellRequests;
    private List<BuyRequest> buyRequests;

    private List<Transaction> transactionHistory;

    private boolean blocked;

    public static Lot create(Item item, List<SellRequest> sellRequests, List<BuyRequest> buyRequests, List<Transaction> transactionHistory, boolean blocked) {
        Lot lot = new Lot();
        lot.setItem(item);
        lot.setSellRequests(sellRequests);
        lot.setBuyRequests(buyRequests);
        lot.setTransactionHistory(transactionHistory);
        lot.setBlocked(blocked);

        return lot;
    }

}
