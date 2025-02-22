package me.asunder.urlShortener.url.repository;

import me.asunder.urlShortener.url.model.ShortUrl;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UrlRepository extends MongoRepository<ShortUrl, String> {}
