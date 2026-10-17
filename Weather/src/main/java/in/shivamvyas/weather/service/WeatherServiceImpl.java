package in.shivamvyas.weather.service;

import in.shivamvyas.weather.client.WeatherApiClient;
import in.shivamvyas.weather.dto.AirQualityInfo;
import in.shivamvyas.weather.dto.WeatherResponse;
import in.shivamvyas.weather.dto.provider.Current;
import in.shivamvyas.weather.dto.provider.Location;
import in.shivamvyas.weather.dto.provider.WeatherApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WeatherServiceImpl implements WeatherService {
    private WeatherApiClient client;

    @Autowired
    public void setClient(WeatherApiClient client) {
        this.client = client;
    }

    @Override
    public WeatherResponse getWeather(String city, boolean includeAqi) {
        // Calling the weather API provider
        WeatherApiResponse response = client.fetchByCity(city, includeAqi);

        // Pulling out the details for easy access
        Current current = response.getCurrent();
        Location location = response.getLocation();

        // fetch air quality details only if it exists, otherwise keep it null
        AirQualityInfo airQuality = null;
        if(current.getAirQuality() != null) {
            airQuality = AirQualityInfo.builder().pm25(current.getAirQuality().getPm25())
                    .pm10(current.getAirQuality().getPm10())
                    .usEpaCategory(toEpaCategory(current.getAirQuality().getUsEpaIndex()))
                    .build();
        }

        // Build our response
        return WeatherResponse.builder().city(location.getName())
                .city(location.getName())
                .region(location.getRegion())
                .country(location.getCountry())
                .localTime(location.getLocaltime())
                .temperatureCelsius(current.getTempCelsius())
                .feelsLikeCelsius(current.getFeelsLikeCelsius())
                .condition(current.getCondition().getText())
                .iconUrl("https:" + current.getCondition().getIcon())
                .humidity(current.getHumidity())
                .windKmph(current.getWindKmph())
                .windDirection(current.getWindDir())
                .uvIndex(current.getUv())
                .airQualityInfo(airQuality)
                .build();
    }

    private String toEpaCategory(int index) {
        switch (index) {
            case 1: return "Good";
            case 2: return "Moderate";
            case 3: return "Unhealthy for sensitive groups";
            case 4:  return "Unhealthy";
            case 5:  return "Very Unhealthy";
            case 6:  return "Hazardous";
            default: return "Unknown";
        }
    }
}
