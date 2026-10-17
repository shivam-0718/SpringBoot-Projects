package in.shivamvyas.weather.dto.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

// A DTO for storing the Current conditions of the given Location
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Current {
    @JsonProperty("temp_c")
    private double tempCelsius;

    @JsonProperty("feelslike_c")
    private double feelsLikeCelsius;

    @JsonProperty("wind_kph")
    private double windKmph;

    @JsonIgnoreProperties("wind_dir")
    private String windDir;

    private int humidity;
    private double uv;
    private Condition condition;

    @JsonProperty("air_quality")
    private AirQuality airQuality;
}