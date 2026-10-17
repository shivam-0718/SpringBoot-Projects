package in.shivamvyas.weather.client;

import in.shivamvyas.weather.dto.provider.WeatherApiError;
import in.shivamvyas.weather.config.WeatherApiProperties;
import in.shivamvyas.weather.dto.provider.WeatherApiResponse;
import in.shivamvyas.weather.exception.CityNotFoundException;
import in.shivamvyas.weather.exception.WeatherApiException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;
import tools.jackson.core.JacksonException;
import tools.jackson.databind.ObjectMapper;

// Calls the weather provider and converts its errors into our own exceptions
@Component
@RequiredArgsConstructor
public class WeatherApiClient {
    private final RestClient weatherRestClient;
    private final WeatherApiProperties properties;
    private final ObjectMapper objectMapper;

    public WeatherApiResponse fetchByCity(String city, boolean includesAqi) {
        String aqi = includesAqi ? "yes" : "no"; // whether user wants aqi details or not, this is given by the user

        try {
            return weatherRestClient.get()
                    .uri("/current.json?key={key}&q={q}&aqi={aqi}", properties.getKey(), city, aqi)
                    .retrieve().body(WeatherApiResponse.class); // filling the placeholders, done by Spring
        } catch (HttpClientErrorException e) {
            WeatherApiError error = parseError(e.getResponseBodyAsString()); // error due to bad request by the user. API provides the error codes
            int code = error.getError().getCode();

            if(code == 1006) {
                throw new CityNotFoundException(city); // 1006 means city not found
            }
            else if(code == 2006 || code == 2007 || code == 2008) { // this means either API key is invalid / disabled / limit exceeded.
                throw new WeatherApiException("API key invalid / disabled / quota exceeded. Check the API key again.");
            }
            throw new WeatherApiException(error.getError().getMessage()); // different message apart from the above
        } catch (HttpServerErrorException e) {
            throw new WeatherApiException("Weather provider is down"); // weather provider service is down
        } catch (ResourceAccessException e) {
            throw new WeatherApiException("Weather provider is unreachable."); // no connection established with the provider / connection timeout
        }
    }

    private WeatherApiError parseError(String body) {
        try {
            return objectMapper.readValue(body, WeatherApiError.class);
        } catch (JacksonException e) {
            throw new WeatherApiException("Unexpected error response from weather provider");
        }
    }
}
