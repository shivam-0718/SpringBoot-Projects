package in.shivamvyas.weather.advice;

import lombok.Getter;
import lombok.Setter;

// Holds the error code and message from the weather provider's error response
@Getter
@Setter
public class ErrorDetails {
    private int code;
    private String message;
}
