package in.shivamvyas.weather.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherResponse {
    private String city;
    private String region;
    private String country;
    private String localTime;
    private double temperatureCelsius;
    private double feelsLikeCelsius;
    private String condition;
    private String iconUrl;
    private int humidity;
    private double windKmph;
    private String windDirection;
    private double uvIndex;
    private AirQualityInfo airQualityInfo;
}
