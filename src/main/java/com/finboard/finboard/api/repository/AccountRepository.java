package com.finboard.finboard.api.repository;

import com.finboard.finboard.api.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    //generuje automatycznie findAll, findById, save, deleteById, count

    List<Account> findByUserId(Long userId);
}
