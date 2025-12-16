package ru.mox.mox_market.entity.tradeEnt;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mox.mox_market.entity.BaseEntity;

@Entity @Getter @Setter @NoArgsConstructor
public class Item extends BaseEntity {

    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String imageUrl;

    public static Item create(String title, String description, String imageUrl) {
        Item item = new Item();
        item.setTitle(title);
        item.setDescription(description);
        item.setImageUrl(imageUrl);

        return item;
    }
}
