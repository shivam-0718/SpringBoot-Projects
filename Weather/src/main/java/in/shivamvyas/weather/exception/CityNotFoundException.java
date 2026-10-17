package in.shivamvyas.weather.exception;

// Thrown when the requested city is not found by the provider
public class CityNotFoundException extends RuntimeException {
    public CityNotFoundException(String city) {
        super("City not found: " + city);
    }
}
