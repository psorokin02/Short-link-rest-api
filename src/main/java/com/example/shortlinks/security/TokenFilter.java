package com.example.shortlinks.security;

import com.example.shortlinks.security.token.TokenProvider;
import com.example.shortlinks.security.token.UserToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
        System.out.println("entered into filter");
        try {
            UserToken token = tokenProvider.resolveToken((HttpServletRequest) servletRequest);
            if (token != null && tokenProvider.validateToken(token)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                if (authentication != null) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        } catch (UserAuthenticationException e) {
            System.out.println("got exception auth");
            //SecurityContextHolder.clearContext();
            //((HttpServletResponse) servletResponse).sendError(e.getHttpStatus().value());
            //throw new UserAuthenticationException("Token is expired or invalid");
            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
