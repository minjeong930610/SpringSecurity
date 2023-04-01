package com.security.corespringsecurity.security.service;

import com.security.corespringsecurity.domain.Account;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;

public class AccountContext extends User {

    private final Account account;
    public AccountContext(Account account, ArrayList<GrantedAuthority> roles){
        //account 객체를 사용하여 전달
        super(account.getUsername(), account.getPassword(), roles);
        this.account = account; //나중에 참조할 수 있도록
    }

    public Account getAccount() {
        return account;
    }
}
