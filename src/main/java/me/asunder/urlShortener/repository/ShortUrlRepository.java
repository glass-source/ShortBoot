package me.asunder.urlShortener.repository;

import me.asunder.urlShortener.models.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.Optional;

public interface ShortUrlRepository extends MongoRepository<ShortUrl, String> {
    Optional<ShortUrl> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}
