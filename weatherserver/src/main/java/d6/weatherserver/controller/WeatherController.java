package d6.weatherserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import d6.weatherserver.service.WeatherService;
import jakarta.json.JsonObject;

@RestController
@RequestMapping(path="/api/weather", produces=MediaType.APPLICATION_JSON_VALUE)
@CrossOrigin("*")
public class WeatherController {

    @Autowired
    WeatherService weaSvc;

    @GetMapping("/{city}")
	public String getCustomer(@PathVariable String city) {

        System.out.println("city is > " +city);

        weaSvc.getWeatherDetails(city);

        System.out.println("humidity is > " 
            +weaSvc.getWeatherDetails(city).getJsonNumber("humidity").intValue());
        System.out.println("json object is > " 
            +weaSvc.getWeatherDetails(city));

        return weaSvc.getWeatherDetails(city).toString();
    }

}
