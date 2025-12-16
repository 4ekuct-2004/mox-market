package ru.mox.mox_market.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mox.mox_market.entity.tradeEnt.Transaction;

import java.time.LocalDateTime;
import java.util.List;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {

    List<Transaction> findBySellerRequestId(Long sellerRequestId, Pageable pageable);
    List<Transaction> findByBuyerRequestId(Long buyerRequestId, Pageable pageable);
    List<Transaction> findByItemInstanceId(Long itemId, Pageable pageable);
    List<Transaction> findByCreatedAtAfter(LocalDateTime createdAt);

    List<Transaction> findBySellerRequestIdOrBuyerRequestId(Long sellerRequestId, Long buyerRequestId);
    default List<Transaction> findByUserId(Long userId) { return findBySellerRequestIdOrBuyerRequestId(userId, userId); }

    List<Transaction> findByLotTransactionHistoryId(Long lotTransactionHistoryId);


}
