package com.musiccatalog.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.security.Key;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void whenNoTokenProvided_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/library"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenInvalidTokenProvided_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/api/v1/library")
                .header("Authorization", "Bearer invalid.token.here"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void whenExpiredTokenProvided_thenUnauthorized() throws Exception {
        Key key = Keys.hmacShaKeyFor("this_is_a_test_secret_key_that_is_at_least_32_bytes_long".getBytes());
        String expiredToken = Jwts.builder()
                .setSubject("testuser")
                .setExpiration(new Date(System.currentTimeMillis() - 10000)) // 10 seconds ago
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        mockMvc.perform(get("/api/v1/library")
                .header("Authorization", "Bearer " + expiredToken))
                .andExpect(status().isUnauthorized());
    }
}
