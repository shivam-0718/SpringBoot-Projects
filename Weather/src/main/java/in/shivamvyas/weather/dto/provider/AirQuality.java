package in.shivamvyas.weather.dto.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

// A DTO for the air quality part of the provider's current weather
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class AirQuality {
    @JsonProperty("pm2_5")
    private double pm25;

    private double pm10;

    @JsonProperty("us-epa-index")
    private int usEpaIndex;
}
