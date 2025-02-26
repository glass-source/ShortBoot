package me.asunder.urlShortener.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "short_urls")
@Data
public class ShortUrl {

    @Id private String id;
    private String originalUrl;
    private String shortCode;
    private long accessCount;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
