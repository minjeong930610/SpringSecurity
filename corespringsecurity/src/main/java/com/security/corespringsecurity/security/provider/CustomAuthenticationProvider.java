package com.security.corespringsecurity.security.provider;

import com.security.corespringsecurity.security.common.FormWebAuthenticationDetails;
import com.security.corespringsecurity.security.service.AccountContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //Authentication 객체는 AuthenticationManager 에게 전달받은 것
        String loginId = authentication.getName();
        String password = (String) authentication.getCredentials();

        AccountContext accountContext = (AccountContext) userDetailsService.loadUserByUsername(loginId);

        if(!passwordEncoder.matches(password, accountContext.getPassword())){
           throw new BadCredentialsException("Invalid password");
        }

        FormWebAuthenticationDetails formWebAuthenticationDetails = (FormWebAuthenticationDetails) authentication.getDetails();
        String secretKey = formWebAuthenticationDetails.getSecretKey();

        if(secretKey == null || !"secret".equals(secretKey)){
            throw new InsufficientAuthenticationException("InsufficientAuthenticationException");
        }

        return new UsernamePasswordAuthenticationToken(accountContext.getAccount(), null, accountContext.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication){
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
