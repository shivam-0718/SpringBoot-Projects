package in.shivamvyas.weather.dto.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.NoArgsConstructor;

// A DTO for the location part of the weather provider's response
@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Location {
    private String name;
    private String region;
    private String country;
    private String localtime;
}
