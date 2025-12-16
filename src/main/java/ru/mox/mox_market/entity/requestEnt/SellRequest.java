package ru.mox.mox_market.entity.requestEnt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mox.mox_market.entity.BaseEntity;
import ru.mox.mox_market.entity.authEnt.MoxUser;
import ru.mox.mox_market.entity.enums.TradeRequestStatus;
import ru.mox.mox_market.entity.tradeEnt.ItemInstance;
import ru.mox.mox_market.entity.tradeEnt.Lot;

import java.math.BigDecimal;

@Entity @Getter @Setter @NoArgsConstructor
public class SellRequest extends BaseEntity {

    @ManyToOne
    private Lot target;
    @ManyToOne
    private MoxUser author;

    @Column(nullable = false)
    private BigDecimal price;

    @OneToOne
    private ItemInstance item;

    TradeRequestStatus status;

    public static SellRequest create(Lot target, MoxUser author, BigDecimal price, ItemInstance item) {
        SellRequest request = new SellRequest();
        request.setTarget(target);
        request.setAuthor(author);
        request.setPrice(price);
        request.setItem(item);
        request.setStatus(TradeRequestStatus.ACTIVE);

        return request;
    }

}
