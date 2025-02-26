package me.asunder.urlShortener.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.asunder.urlShortener.dto.ShortUrlDTO;
import me.asunder.urlShortener.models.ShortUrl;
import me.asunder.urlShortener.repository.ShortUrlRepository;
import me.asunder.urlShortener.services.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ShortUrlController {

    private final ShortUrlService service;
    private final ShortUrlRepository repository;

    @PostMapping("/shorten")
    public ResponseEntity<?> createShortUrl (@Valid @RequestBody ShortUrlDTO.CreateShortUrlRequest request) {
        try {
            ShortUrlDTO.CreateShortUrlResponse response = service.createShortUrl(request.getUrl());
            return ResponseEntity.created(URI.create("/" + response.getShortCode())).body(response);
        } catch (IllegalArgumentException ex) {
            return handleValidationExceptions(ex);
        }
    }

    @GetMapping("/shorten/{shortCode}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortCode) {
        try {
            ShortUrl shortUrl = repository.findByShortCode(shortCode).orElseThrow(() -> new IllegalArgumentException("Short URL not found"));
            shortUrl.setAccessCount(shortUrl.getAccessCount() + 1);
            repository.save(shortUrl);
            ShortUrlDTO.GetOriginalUrlResponse response = service.getOriginalUrl(shortCode);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return handleValidationExceptions(e);
        }
    }

    @DeleteMapping("/shorten/{shortCode}")
    public ResponseEntity<?> deleteShortUrl(@PathVariable String shortCode) {
        try {
            ShortUrl shortUrl = repository.findByShortCode(shortCode).orElseThrow(() -> new IllegalArgumentException("Short URL not found"));
            repository.delete(shortUrl);
            return ResponseEntity.ok("Short URL deleted");
        } catch (IllegalArgumentException ex) {
            return handleValidationExceptions(ex);
        }
    }

    @PutMapping("/shorten/{shortCode}")
    public ResponseEntity<?> updateShortUrl(@Valid @RequestBody ShortUrlDTO.CreateShortUrlRequest request, @PathVariable String shortCode) {
        try {
            ShortUrlDTO.CreateShortUrlResponse response = service.updateShortUrl(shortCode, request.getUrl());
            return ResponseEntity.created(URI.create("/" + response.getShortCode())).body(response);
        } catch (IllegalArgumentException ex) {
            return handleValidationExceptions(ex);
        }
    }

    private ResponseEntity<?> handleValidationExceptions(IllegalArgumentException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put("error: ", ex.getMessage());
        return ResponseEntity.badRequest().body(errors);
    }

}