package me.asunder.urlShortener.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

public class ShortUrlDTO {

    @Data
    public static class CreateShortUrlRequest {
        @NotBlank(message = "URL is required")
        @Pattern(regexp = "^(https?|ftp)://[^\\s/$.?#].\\S*$",
                message = "Invalid URL format")
        private String url;
    }

    @Data
    public static class CreateShortUrlResponse {
        private String id;
        private String url;
        private String shortCode;
        private long accessCount;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime createdAt;

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
        private LocalDateTime updatedAt;
    }

    @Getter
    @AllArgsConstructor
    public static class GetOriginalUrlResponse {
        private String originalUrl;
        private long accessCount;
    }


}
