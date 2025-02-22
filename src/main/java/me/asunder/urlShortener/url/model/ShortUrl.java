package me.asunder.urlShortener.url.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Document(collection = "short_urls")
public class ShortUrl {

    @Id private String id;
    @Getter @Setter private String originalUrl;
    @Getter @Setter private String shortCode;
    @Getter @Setter private LocalDateTime createdDate;
    @Getter @Setter private Long accessCount = 0L;

}
