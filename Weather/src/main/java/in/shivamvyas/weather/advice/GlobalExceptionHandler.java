package in.shivamvyas.weather.advice;

import in.shivamvyas.weather.exception.CityNotFoundException;
import in.shivamvyas.weather.exception.WeatherApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.method.ParameterValidationResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // City does not exist -> 404
    @ExceptionHandler(CityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleCityNotFound(CityNotFoundException cnfe) {
        return buildResponse(HttpStatus.NOT_FOUND, cnfe.getMessage(), null);
    }

    // WeatherAPI failed (down, timeout, bad key) -> 502 Bad Gateway
    // 502 tells the caller "the problem is the provider behind us, not our code"
    @ExceptionHandler(WeatherApiException.class)
    public ResponseEntity<ErrorResponse> handleWeatherApiException(WeatherApiException ex) {
        return buildResponse(HttpStatus.BAD_GATEWAY, ex.getMessage(), null);
    }

    // @NotBlank on @RequestParam failed -> 400
    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ErrorResponse> handleValidation(HandlerMethodValidationException ex) {

        Map<String, String> details = new LinkedHashMap<>();

        // one result per validated parameter (here: city)
        for (ParameterValidationResult result : ex.getParameterValidationResults()) {

            String paramName = result.getMethodParameter().getParameterName();

            // one error per failed rule on that parameter
            for (MessageSourceResolvable error : result.getResolvableErrors()) {
                details.put(paramName, error.getDefaultMessage());
            }
        }
        return buildResponse(HttpStatus.BAD_REQUEST, "Validation failed", details);
    }

    // Anything we did not expect -> 500, with a safe generic message
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleUnexpected(Exception ex) {
        log.error("Unexpected error", ex);    // full stack trace goes to the log only
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", null);
    }

    // Helper so every handler builds the response the same way
    private ResponseEntity<ErrorResponse> buildResponse(HttpStatus status, String message,
                                                        Map<String, String> details) {
        ErrorResponse body = ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(status.value())
                .error(status.getReasonPhrase())
                .message(message)
                .details(details)
                .build();

        return ResponseEntity.status(status).body(body);
    }
}