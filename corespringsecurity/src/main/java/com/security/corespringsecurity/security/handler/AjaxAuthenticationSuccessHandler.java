package com.security.corespringsecurity.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.security.corespringsecurity.domain.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AjaxAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        //인증에 성공한 정보
        Account account = (Account) authentication.getPrincipal(); //account 객체 (AjaxAuthenticationToken(accountContext.getAccount)

        response.setStatus(HttpStatus.OK.value());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);

        objectMapper.writeValue(response.getWriter(), account); //objectMapper가 json형태로 변환하여 다시 client로 전달
    }
}
