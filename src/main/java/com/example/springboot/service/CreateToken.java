package com.example.springboot.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.springboot.pojo.SysUser;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class CreateToken {
    public String getToken(SysUser user) {
        String token="";
        token= JWT.create().withAudience(user.getId()).withExpiresAt(new Date(System.currentTimeMillis() + 30 * 1000))
                .sign(Algorithm.HMAC256(user.getPassword()));
        return token;
    }
}
