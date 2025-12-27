package ru.mox.mox_market.service;

import org.springframework.stereotype.Service;
import ru.mox.mox_market.entity.authEnt.MoxUser;
import ru.mox.mox_market.entity.tradeEnt.Inventory;
import ru.mox.mox_market.entity.tradeEnt.Item;
import ru.mox.mox_market.entity.tradeEnt.ItemInstance;
import ru.mox.mox_market.repository.InventoryRepo;

import java.math.BigDecimal;
import java.util.List;

@Service
public class InventoryService {

    private final InventoryRepo inventoryRepo;

    public InventoryService(InventoryRepo inventoryRepo) {
        this.inventoryRepo = inventoryRepo;
    }

    public void createInventoryForUser(MoxUser owner) {
        Inventory inventory = Inventory.of(owner);
        owner.setInventory(inventory);
        inventoryRepo.save(inventory);
    }

    public void moveItem(Inventory in, Inventory out, ItemInstance item) {
        if(!out.getItems().contains(item)) throw new IllegalArgumentException("InventoryService.moveItem | Out inv dont have needed item");

        List<ItemInstance> outItems = out.getItems();
        outItems.remove(item);
        out.setItems(outItems);

        List<ItemInstance> inItems = in.getItems();
        inItems.add(item);
        in.setItems(inItems);
    }

    public Inventory getUserInventory(MoxUser user) {
        return inventoryRepo.findByOwnerId(user.getId())
                .orElseThrow(() -> new IllegalArgumentException("InventoryService.getUserInventory | Inventory does not exist"));
    }

    public BigDecimal getInventoryMonetaryVolume(MoxUser user) {
        return inventoryRepo.getInventoryMonetaryVolumeByOwnerId(user.getId());
    }

    public void changeUserBalance(MoxUser user, BigDecimal newBalance) {
        Inventory inv = getUserInventory(user);
        inv.setBalance(newBalance);
    }

    public void reduceUserBalance(MoxUser user, BigDecimal num) {
        Inventory inv = getUserInventory(user);
        checkPayPossibility(inv, num);

        inv.setBalance(inv.getBalance().subtract(num));
    }
    public void increaseUserBalance(MoxUser user, BigDecimal num) {
        Inventory inv = getUserInventory(user);
        inv.setBalance(inv.getBalance().add(num));
    }

    public void holdUserMoney(MoxUser user, BigDecimal num) {
        Inventory inv = getUserInventory(user);
        checkPayPossibility(inv, num);

        inv.setBalance(inv.getBalance().subtract(num));
        inv.setWithholdenBalance(inv.getWithholdenBalance().add(num));
    }
    public void unholdUserMoney(MoxUser user, BigDecimal num) {
        Inventory inv = getUserInventory(user);
        checkWHPayPossibility(inv, num);

        inv.setBalance(inv.getBalance().add(num));
        inv.setWithholdenBalance(inv.getWithholdenBalance().subtract(num));
    }
    public void reduceUserWHBalance(MoxUser user, BigDecimal num) {
        Inventory inv = getUserInventory(user);

        checkWHPayPossibility(inv, num);

        inv.setWithholdenBalance(inv.getWithholdenBalance().subtract(num));
    }

    private void checkPayPossibility(Inventory inv, BigDecimal num) {
        if(inv.getBalance().compareTo(num) < 0) throw new IllegalArgumentException("InventoryService.checkPayPossibility | User does not have enough money");
    }
    private void checkWHPayPossibility(Inventory inv, BigDecimal num) {
        if(inv.getWithholdenBalance().compareTo(num) < 0) throw new IllegalArgumentException("InventoryService.checkWHPayPossibility | User does not have enough money");
    }

}
