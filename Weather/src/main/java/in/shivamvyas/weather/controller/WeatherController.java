package in.shivamvyas.weather.controller;

import in.shivamvyas.weather.dto.WeatherResponse;
import in.shivamvyas.weather.service.WeatherService;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/weather")
public class WeatherController {

    @Autowired
    private WeatherService service;

    @GetMapping()
    public ResponseEntity<WeatherResponse> getWeather(@RequestParam @NotBlank(message = "City cannot be blank") String city, @RequestParam(defaultValue = "false") boolean includeAqi) {
        WeatherResponse response = service.getWeather(city, includeAqi);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
