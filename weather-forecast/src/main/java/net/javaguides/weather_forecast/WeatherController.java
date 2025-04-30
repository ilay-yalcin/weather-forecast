package net.javaguides.weather_forecast;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WeatherController {

    @Autowired
    private WeatherService weatherService;

    @GetMapping("/weather")
    public String getWeather(@RequestParam("city") String city, Model model) {
        try {
            WeatherData weatherData = weatherService.getAverageWeatherData(city);
            
            model.addAttribute("city", city);
            model.addAttribute("temperature", weatherData.getTemperature());
            model.addAttribute("humidity", weatherData.getHumidity());
            model.addAttribute("windSpeed", weatherData.getWindSpeed());
            model.addAttribute("weatherIcon", "wi-day-sunny");

        } catch (Exception e) {
            model.addAttribute("error", "Error fetching weather data: " + e.getMessage());
        }

        return "weather"; // weather.html
    }
}
