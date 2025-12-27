package ru.mox.mox_market.service;

import org.springframework.stereotype.Service;
import ru.mox.mox_market.entity.enums.TradeRequestStatus;
import ru.mox.mox_market.entity.tradeEnt.Inventory;
import ru.mox.mox_market.entity.tradeEnt.Lot;
import ru.mox.mox_market.entity.tradeEnt.Transaction;
import ru.mox.mox_market.repository.TransactionRepo;

import java.util.List;

@Service
public class TransactionService {

    private final TransactionRepo transactionRepo;

    private final InventoryService inventoryService;
    private final LotService lotService;

    public TransactionService(TransactionRepo transactionRepo, InventoryService inventoryService, LotService lotService) {
        this.transactionRepo = transactionRepo;
        this.inventoryService = inventoryService;
        this.lotService = lotService;
    }

    public void applyTransaction(Transaction transaction) {
        Inventory out = transaction.getSellerRequest().getAuthor().getInventory();
        Inventory in = transaction.getBuyerRequest().getAuthor().getInventory();
        Lot lot = transaction.getBuyerRequest().getTarget();

        inventoryService.reduceUserWHBalance(transaction.getBuyerRequest().getAuthor(), transaction.getPrice());
        inventoryService.increaseUserBalance(transaction.getSellerRequest().getAuthor(), transaction.getPrice());
        inventoryService.moveItem(in, out, transaction.getItemInstance());

        List<Transaction> history = lot.getTransactionHistory();
        history.add(transaction);
        lot.setTransactionHistory(history);

        transaction.getBuyerRequest().setStatus(TradeRequestStatus.CONDUCTED);
        transaction.getSellerRequest().setStatus(TradeRequestStatus.CONDUCTED);

        transactionRepo.save(transaction);
    }

}
