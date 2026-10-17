package in.shivamvyas.weather.config;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;
import java.time.Duration;

// Binds and validates the weather.api.* settings from application.properties
@Validated
@Getter
@Setter
@ConfigurationProperties(prefix = "weather.api")
public class WeatherApiProperties {
    @NotBlank(message = "Base URL cannot be blank.")
    private String baseUrl;

    @NotBlank(message = "API key is not configured. Configure the API key. Ensure DO NOT COMMIT the API key.")
    private String key;

    @NotNull
    private Duration timeout;
}