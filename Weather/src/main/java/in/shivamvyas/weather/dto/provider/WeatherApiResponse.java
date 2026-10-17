package in.shivamvyas.weather.dto.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

// Top-level DTO for the weather provider's response (location + current weather)
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherApiResponse {
    private Location location;
    private Current current;
}
