package in.shivamvyas.weather.exception;

// Thrown when the weather provider call fails
public class WeatherApiException extends RuntimeException {
    public WeatherApiException(String message) {
        super(message);
    }
}
