package ru.mox.mox_market.entity.tradeEnt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mox.mox_market.entity.BaseEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ItemInstance extends BaseEntity {

    @ManyToOne
    private Item item;

    private boolean onSell;

}
