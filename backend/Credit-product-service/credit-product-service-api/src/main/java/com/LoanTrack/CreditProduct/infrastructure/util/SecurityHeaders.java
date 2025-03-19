package com.LoanTrack.Backend.infrastructure.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityHeaders {

    @Value("${security.headers.key}")
    private String key;

    @Value("${security.headers.token}")
    private String token;

    public Boolean validate(String key, String token){
        if(this.key.equals(key) && this.token.equals(token)){
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
    }
}
