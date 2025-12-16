package ru.mox.mox_market.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.mox.mox_market.entity.authEnt.MoxUser;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<MoxUser, Long> {

    Optional<MoxUser> findById(Long id);
    Optional<MoxUser> findByUsername(String username);

    List<MoxUser> findByPublicNameContainingAndDeletedIsFalse(String publicName, Pageable pageable);
    List<MoxUser> findByUsernameContainingAndDeletedIsFalse(String username, Pageable pageable);

    List<MoxUser> findByTradingVolBetweenAndDeletedIsFalse(long start, long end, Pageable pageable);

    List<MoxUser> findTop10ByTradingVolAndDeletedIsFalse(Pageable pageable);

}
