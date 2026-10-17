package in.shivamvyas.weather;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

// @ConfigurationPropertiesScan annotation scans and registers @ConfigurationProperties classes
@SpringBootApplication
@ConfigurationPropertiesScan
public class WeatherApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeatherApplication.class, args);
    }

}
