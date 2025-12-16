package ru.mox.mox_market.entity.requestEnt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class BuyRequest extends BaseEntity {

    @ManyToOne
    private Lot target;
    @ManyToOne
    private MoxUser author;

    @Column(nullable = false)
    private BigDecimal price;

    TradeRequestStatus status;

    public static BuyRequest create(Lot target, MoxUser author, BigDecimal price) {
        BuyRequest request = new BuyRequest();
        request.setTarget(target);
        request.setAuthor(author);
        request.setPrice(price);
        request.status = TradeRequestStatus.ACTIVE;

        return request;
    }

}