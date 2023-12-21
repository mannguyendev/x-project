package com.mnguyendev.xproject.common;

import com.mnguyendev.xproject.common.identifier.GenerateUUID;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

public class CommonUtils {
    private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);

    public static String generateUUID() {
        UUID uuid = UUID.randomUUID();
        log.info("Generate UUID: " + uuid.toString());
        return uuid.toString();
    }

    public static String generateJWTCode(){
        Date now = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(now);
        c.add(Calendar.MONTH, 1);
        Date expDate = c.getTime();

        String token = Jwts.builder().setId("Test123")
                .setIssuer("xproject")
                .setSubject("logintoken")
                .claim("name", "Man Nguyen Dev")
                .claim("scope", "admins")
                // Fri Jun 24 2016 15:33:42 GMT-0400 (EDT)
                .setIssuedAt(now)
                // Sat Jun 24 2116 15:33:42 GMT-0400 (EDT)
                .setExpiration(expDate)
                .signWith(
                        SignatureAlgorithm.HS256,
                        TextCodec.BASE64.decode("Yn2kjibddFAWtnPJ2AFlL8WXmohJMCvigQggaEypa5E=")
                )
                .compact();
        log.info("Generate token: " + token);
        return token;
    }
}
