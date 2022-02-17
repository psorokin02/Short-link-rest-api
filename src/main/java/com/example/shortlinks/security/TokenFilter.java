package com.example.shortlinks.security;

import com.example.shortlinks.security.token.TokenProvider;
import com.example.shortlinks.security.token.UserToken;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class TokenFilter extends GenericFilterBean {
    private TokenProvider tokenProvider;

    public TokenFilter(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        UserToken token = tokenProvider.resolveToken((HttpServletRequest) servletRequest);
        if(token == null || !tokenProvider.validateToken(token)){
            System.out.println("filter: forbidden");
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_FORBIDDEN);
            System.out.println("filter: forbidden");
            return;
        }
        System.out.println("filter: ok");
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
