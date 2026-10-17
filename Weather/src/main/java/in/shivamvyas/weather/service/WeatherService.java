package in.shivamvyas.weather.service;

import in.shivamvyas.weather.dto.WeatherResponse;

public interface WeatherService {
    WeatherResponse getWeather(String city, boolean includeAqi);
}
