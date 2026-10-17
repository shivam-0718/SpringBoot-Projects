package in.shivamvyas.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

// Simplified air quality data returned by our own API
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AirQualityInfo {
    private double pm25;
    private double pm10;
    private String usEpaCategory;
}
