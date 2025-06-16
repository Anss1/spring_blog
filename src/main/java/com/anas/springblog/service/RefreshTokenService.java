package com.anas.springblog.service;

import com.anas.springblog.model.RefreshToken;
import com.anas.springblog.model.User;
import com.anas.springblog.repository.RefreshTokenRepository;
import com.anas.springblog.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final static Long refreshTokenDurationMs = 604800000L; // 7 days = 1000 * 60 * 60 * 24 * 7

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken createRefreshToken(User user) {

        // check if the token expired before login
        refreshTokenRepository.findByUser(user).ifPresent(existing -> {
            if (existing.getExpiryDate().isBefore(Instant.now())) {
                refreshTokenRepository.delete(existing);
            }
        });
        // generating the token
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userRepository.findById(user.getId()).orElseThrow());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }
    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
        refreshTokenRepository.deleteByUser(user);
    }
}
