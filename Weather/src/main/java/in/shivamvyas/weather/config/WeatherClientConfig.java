package in.shivamvyas.weather.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestClient;

// Creates the RestClient used to call the weather provider
@Configuration
public class WeatherClientConfig {

    // Apply the configured timeout to both connecting and reading
    @Bean
    public RestClient weatherRestClient(WeatherApiProperties properties) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout((int) properties.getTimeout().toMillis());
        factory.setReadTimeout((int) properties.getTimeout().toMillis());

        return RestClient.builder().baseUrl(properties.getBaseUrl())
                .requestFactory(factory)
                .build();

    }
}
