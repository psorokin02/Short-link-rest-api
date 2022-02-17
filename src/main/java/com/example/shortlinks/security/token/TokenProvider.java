package com.example.shortlinks.security.token;

import com.example.shortlinks.security.user.User;
import com.example.shortlinks.security.user.UserService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Map;

@Component
public class TokenProvider {
    private UserService userService;
    private TokenHashCoder tokenHashCoder;

    public TokenProvider(UserService userService, TokenHashCoder tokenHashCoder) {
        this.userService = userService;
        this.tokenHashCoder = tokenHashCoder;
    }

    public String createToken(User user, HttpServletRequest request){
        StringBuilder builder = new StringBuilder();
        Map<String, String[]> params = request.getParameterMap();

        System.out.println(params.toString());
        params.forEach((key, valuesArray) -> {
            Arrays.stream(valuesArray).forEach(
                    value -> builder.append(key).append("=").append(value).append("&")
            );
        });
        builder.append("secret").append("=").append(user.getSecret());

        System.out.println("params = " + builder);
        return tokenHashCoder.createToken(builder.toString());
    }

    public boolean validateToken(UserToken token){
        UserToken userToken = userService.getUserTokenById(token.getId());
        if(userToken == null || !userToken.getToken().equals(token.getToken())){
            return false;
        }

        String data = tokenHashCoder.decodeToken(token.getToken());
        String[] params = data.split("&");
        String[] secret = params[params.length-1].split("=");

        if(secret.length != 2){
            return false;
        }

        User user = userService.getUserById(token.getId());
        return secret[0].equals("secret") && secret[1].equals(user.getSecret());
    }

    public UserToken resolveToken(HttpServletRequest request) {
        if(request.getParameter("token") == null || request.getParameter("id") == null){
            return null;
        }
        String token =  request.getParameter("token");
        Long id = Long.valueOf(request.getParameter("id"));
        return new UserToken(id,token);
    }
}
