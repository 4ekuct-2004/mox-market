package ru.mox.mox_market.entity.tradeEnt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mox.mox_market.entity.BaseEntity;
import ru.mox.mox_market.entity.requestEnt.BuyRequest;
import ru.mox.mox_market.entity.requestEnt.SellRequest;

import java.math.BigDecimal;

@Entity @Getter @Setter @NoArgsConstructor
public class Transaction extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    private SellRequest sellerRequest;
    @OneToOne(fetch = FetchType.EAGER)
    private BuyRequest buyerRequest;

    private BigDecimal price;

    @OneToOne(fetch = FetchType.EAGER)
    private ItemInstance itemInstance;

    @ManyToOne(fetch = FetchType.EAGER)
    private Lot lot;

    public static Transaction create(SellRequest seller, BuyRequest buyer, ItemInstance item, BigDecimal price, Lot lot) {
        Transaction transaction = new Transaction();
        transaction.setSellerRequest(seller);
        transaction.setBuyerRequest(buyer);
        transaction.setPrice(price);
        transaction.setItemInstance(item);
        transaction.setLot(lot);

        return transaction;
    }
}
