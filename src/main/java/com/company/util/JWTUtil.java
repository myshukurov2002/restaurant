package com.company.util;


import com.company.dto.auth.JwtDTO;
import com.company.enums.ProfileRole;
import com.company.exp.UnAuthorizedException;
import io.jsonwebtoken.*;

import java.util.Date;
import java.util.List;

public class JWTUtil {
    public static final String secretKey = "!mazgi_pazgi234^sad***-*-*+*-0293nd42839+nyf423gikeyodun298143ydrijom!mh9l8e]fhwl8eh-f!=-=-=9-0lnlsdkf__sdasd_dasdoioio283y492d3ny28]97+3y5bf,82+37t?";
    public static final long tokenLiveTime = 1000*3600*24*10; // 10day //TODO  1 hour
    public static final long roleTokenLiveTime = 1000*3600*24 *10; // 10 day //TODO  1 day

    public static String encode(String id, List<ProfileRole> roles) {
        JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setIssuedAt(new Date());
        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);

        jwtBuilder.claim("id", id);
        jwtBuilder.claim("roles", roles);

        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (tokenLiveTime)));
        jwtBuilder.setIssuer("restaurant PORTAL");
        return jwtBuilder.compact();
    }
    public static JwtDTO decode(String token) {
        try {
            JwtParser jwtParser = Jwts.parser();
            jwtParser.setSigningKey(secretKey);

            Jws<Claims> jws = jwtParser.parseClaimsJws(token);

            Claims claims = jws.getBody();

            String id = (String) claims.get("id");
            List<ProfileRole> roles = (List<ProfileRole>) claims.get("roles");

            return new JwtDTO(id, roles);
        } catch (JwtException e) {
            throw new UnAuthorizedException("Your session expired");
        }
    }

//    public static String encodePhoneJwt(String profileId) { //TODO
//        JwtBuilder jwtBuilder = Jwts.builder();
//        jwtBuilder.setIssuedAt(new Date());
//        jwtBuilder.signWith(SignatureAlgorithm.HS512, secretKey);
//
//        jwtBuilder.claim("id", profileId);
//
//        jwtBuilder.setExpiration(new Date(System.currentTimeMillis() + (roleTokenLiveTime)));
//        jwtBuilder.setIssuer("restaurant PORTAL");
//        return jwtBuilder.compact();
//    }
//
//    public static JwtDTO decodeEmailJwt(String token) {
//        try {
//            JwtParser jwtParser = Jwts.parser();
//            jwtParser.setSigningKey(secretKey);
//            Jws<Claims> jws = jwtParser.parseClaimsJws(token);
//            Claims claims = jws.getBody();
//            String id = (String) claims.get("id");
//            return new JwtDTO(id);
//        } catch (JwtException e) {
//            throw new UnAuthorizedException("Your session expired");
//        }
//    }
}