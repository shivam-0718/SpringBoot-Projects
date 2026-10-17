package in.shivamvyas.weather.dto.provider;

import in.shivamvyas.weather.advice.ErrorDetails;
import lombok.Getter;
import lombok.Setter;

// Wrapper for the provider's error response body: { "error": { "code", "message" } }
@Getter
@Setter
public class WeatherApiError {
    private ErrorDetails error;
}
