package com.yeongbee.store.mydelight.blog.service;

import com.yeongbee.store.mydelight.blog.domain.HasRole;
import com.yeongbee.store.mydelight.blog.domain.account.Account;
import com.yeongbee.store.mydelight.blog.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSecurityService implements UserDetailsService {

    private final AccountService accountService;
    private final AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> accountOptional = accountRepository.findByUsername(username);
        if (accountOptional.isEmpty()) {
            throw new UsernameNotFoundException("없음");
        }
        Account account = accountOptional.get();

        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        if("admin".equals(username)) {
            grantedAuthorities.add(new SimpleGrantedAuthority(HasRole.ADMIN.getValue()));
        }
        else {
            grantedAuthorities.add(new SimpleGrantedAuthority(HasRole.USER.getValue()));
        }
        return new User(account.getUsername(), account.getPassword(), grantedAuthorities);
    }

}
