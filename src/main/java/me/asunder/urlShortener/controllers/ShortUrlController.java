package me.asunder.urlShortener.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.asunder.urlShortener.dto.ShortUrlDTO;
import me.asunder.urlShortener.models.ShortUrl;
import me.asunder.urlShortener.repository.ShortUrlRepository;
import me.asunder.urlShortener.services.ShortUrlService;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
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
    public ResponseEntity<?> createShortUrl (@Valid @RequestBody ShortUrlDTO.CreateShortUrlRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errors = new HashMap<>();
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
            System.out.println(errors);
            return ResponseEntity.badRequest().body(errors);
        }

        ShortUrlDTO.CreateShortUrlResponse response = service.createShortUrl(request.getUrl());
        return ResponseEntity.created(URI.create("/" + response.getShortCode())).body(response);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortCode) {
        try {
            ShortUrl shortUrl = repository.findByShortCode(shortCode).orElseThrow(() -> new IllegalArgumentException("Short URL not found"));
            shortUrl.setAccessCount(shortUrl.getAccessCount() + 1);
            repository.save(shortUrl);
            ShortUrlDTO.GetOriginalUrlResponse response = service.getOriginalUrl(shortCode);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Short URL not found");
            return ResponseEntity.status(404).body(error);
        }
    }





}
