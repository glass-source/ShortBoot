package me.asunder.urlShortener.services;

import lombok.RequiredArgsConstructor;
import me.asunder.urlShortener.dto.ShortUrlDTO;
import me.asunder.urlShortener.dto.ShortUrlDTO.CreateShortUrlResponse;
import me.asunder.urlShortener.models.ShortUrl;
import me.asunder.urlShortener.repository.ShortUrlRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class ShortUrlService {

    private final ShortUrlRepository repository;
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int CODE_LENGTH = 6;
    private final Random random = new SecureRandom();

    public CreateShortUrlResponse createShortUrl(String originalUrl) {
        String shortCode;
        do {
            shortCode = generateRandomCode(CODE_LENGTH + random.nextInt(4));
        } while (repository.existsByShortCode(shortCode));

        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setOriginalUrl(originalUrl);
        shortUrl.setAccessCount(0);
        shortUrl.setShortCode(shortCode);
        shortUrl.setCreatedAt(LocalDateTime.now());
        shortUrl.setUpdatedAt(LocalDateTime.now());

        ShortUrl saved = repository.save(shortUrl);
        return mapToResponse(saved);
    }

    public ShortUrlDTO.GetOriginalUrlResponse getOriginalUrl(String shortCode) {
        ShortUrl shortUrl = repository.findByShortCode(shortCode).orElseThrow(() -> new IllegalArgumentException("Short URL not found"));
        return new ShortUrlDTO.GetOriginalUrlResponse(shortUrl.getOriginalUrl(), shortUrl.getAccessCount());
    }

    private String generateRandomCode(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    private CreateShortUrlResponse mapToResponse(ShortUrl entity) {
        CreateShortUrlResponse response = new CreateShortUrlResponse();
        response.setId(entity.getId());
        response.setUrl(entity.getOriginalUrl());
        response.setShortCode(entity.getShortCode());
        response.setAccessCount(entity.getAccessCount());
        response.setCreatedAt(entity.getCreatedAt());
        response.setUpdatedAt(entity.getUpdatedAt());
        return response;
    }
}
