package d6.weatherserver.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;

@Service
public class WeatherService {
    
    // variable declaration
    public static final String URL_WEATHER = "https://api.openweathermap.org/data/2.5/weather";
    public static final String URL_ICON = "http://openweathermap.org/img/wn/";
    private final static Logger logging = LoggerFactory.getLogger(WeatherService.class);
    private String main;
    private String description = "";
    private String icon;

    public JsonObject getWeatherDetails (String location) throws IOException {

        // variable declaration
        final String appKey;

        // object instantiation
        String key = System.getenv("W_API_KEY"); // get API key
        if ((null != key) && (key.trim().length() > 0))
            appKey = key;
        else
            appKey = "";

        // build url and call for weather details, getting json object
        final String url = UriComponentsBuilder
                .fromUriString(URL_WEATHER)
                .queryParam("q", location)
                .queryParam("appid", appKey) // get API key
                .queryParam("units", "metric")
                .toUriString();
        final RequestEntity<Void> req = RequestEntity.get(url).build();
        final RestTemplate template = new RestTemplate();
        final ResponseEntity<String> resp = template.exchange(req, String.class);

        if (resp.getStatusCode() != HttpStatus.OK) // if bad response
            throw new IllegalArgumentException(
                "Error: status code %s".formatted(resp.getStatusCode().toString())
            );
        final String body = resp.getBody(); // if ok response
        logging.info("payload: %s".formatted(body));

        // filter entire weather json object to obtain relevant fields only
        JsonObject result = Json.createObjectBuilder().build();

        try (InputStream is = new ByteArrayInputStream(body.getBytes())) {
            final JsonReader reader = Json.createReader(is);
            result = reader.readObject();
            logging.info("result " +result);
        } catch (NullPointerException e) { 
            e.printStackTrace();
        }

        final JsonArray weatherJArray = result.getJsonArray("weather");
        if (!weatherJArray.isEmpty()) {
            JsonObject weatherJArrayObj = weatherJArray.getJsonObject(0);
            main = weatherJArrayObj.getString("main");
            description = weatherJArrayObj.getString("description");
            icon = weatherJArrayObj.getString("icon");
        }

        final String cityName = result.getString("name");
        final double temperature = 
            result.getJsonObject("main").getJsonNumber("temp").doubleValue();
        final int humidity = 
            result.getJsonObject("main").getJsonNumber("humidity").intValue();

        // construct json object to return to Angular
        JsonObject cityWeatherJson = Json.createObjectBuilder()
            .add("cityName", cityName)
            .add("main", main)
            .add("description", description)
            .add("icon", icon)
            .add("temperature", temperature)
            .add("humidity", humidity)
            .build();

        return cityWeatherJson;

    }
    // final String iconUrl = URL_ICON.concat(w.getIcon()).concat("@2x.png");

}
