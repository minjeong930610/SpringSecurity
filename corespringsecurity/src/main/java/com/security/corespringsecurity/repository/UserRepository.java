package com.security.corespringsecurity.repository;

import com.security.corespringsecurity.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Account, Long> {

    Account findByUsername(String username);

    int countByUsername(String username);
}
