package com.bighu.common.utils;


import com.bighu.common.exception.LeaseException;
import com.bighu.common.result.ResultCodeEnum;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.SecretKey;
import java.rmi.dgc.Lease;
import java.util.Date;

public class JwtUtil {
    private static final SecretKey secretKey = Keys.hmacShaKeyFor("123BiHAGOUYFGUYATfy07y8y9283gohgfghu.123*^$%$#".getBytes());
    private static final Logger log = LoggerFactory.getLogger(JwtUtil.class);

    public static String createToken(Long userId, String username) {

        String jwt = Jwts.builder().
                setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60*100)).
                setSubject("LOGIN_USER").
                claim("userId", userId).
                claim("username", username).
                signWith(secretKey, SignatureAlgorithm.HS256).compact();


        return jwt;
    }
    public static Claims parseToken(String token)
    {
        if (token==null)
            throw new LeaseException(ResultCodeEnum.ADMIN_LOGIN_AUTH);
        try {
            JwtParser jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();
//            Jws<Claims> claimsJws = jwtParser.parseClaimsJws(token);
            return jwtParser.parseClaimsJws(token).getBody();
        }
        catch (ExpiredJwtException e)
        {
            throw new LeaseException(ResultCodeEnum.TOKEN_EXPIRED);
        }
        catch (JwtException e)
        {
            log.error(e.getMessage());
//            System.out.println();
            e.printStackTrace();
            throw new LeaseException(ResultCodeEnum.TOKEN_INVALID);
        }




    }

    public static void main(String[] args) {
        System.out.println(createToken(2L, "user"));
    }
}
