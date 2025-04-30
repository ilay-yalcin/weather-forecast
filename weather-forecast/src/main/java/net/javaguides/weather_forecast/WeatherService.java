package net.javaguides.weather_forecast;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.javaguides.OpenWeatherResponse;
import net.javaguides.WeatherStackResponse;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherService {

    private final RestTemplate restTemplate;

    public WeatherService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    // OpenWeatherMap API'den veri al
    @Async
    public CompletableFuture<WeatherData> getWeatherDataFromApi1(String city) {
        String url = "https://api.openweathermap.org/data/2.5/weather?q=" + city +
                     "&appid=a935352aa6d689689451c4d35c0a4b83" + "&units=metric";
        
        OpenWeatherResponse response = restTemplate.getForObject(url, OpenWeatherResponse.class);
        
        if (response != null) {
            return CompletableFuture.completedFuture(
                new WeatherData(response.getMain().getTemp(), 
                                response.getMain().getHumidity(), 
                                response.getWind().getSpeed()));
        } else {
            return CompletableFuture.failedFuture(new RuntimeException("OpenWeather API failed"));
        }
    }

    // Weatherstack API'den veri al
    @Async
    public CompletableFuture<WeatherData> getWeatherDataFromApi2(String city) {
        String url = "http://api.weatherstack.com/current?access_key=ae25da61189a1d8bca39f37d691c45bd&query=" + city;
        
        WeatherStackResponse response = restTemplate.getForObject(url, WeatherStackResponse.class);
        
        if (response != null) {
            return CompletableFuture.completedFuture(
                new WeatherData(response.getCurrent().getTemperature(),
                                response.getCurrent().getHumidity(),
                                response.getCurrent().getWindSpeed()));
        } else {
            return CompletableFuture.failedFuture(new RuntimeException("Weatherstack API failed"));
        }
    }

    // API verilerini alıp ortalama hesaplama
    public WeatherData getAverageWeatherData(String city) throws Exception {
        CompletableFuture<WeatherData> api1Future = getWeatherDataFromApi1(city);
        CompletableFuture<WeatherData> api2Future = getWeatherDataFromApi2(city);
        
        // Asenkron işlemlerin tamamlanmasını bekleyin
        CompletableFuture.allOf(api1Future, api2Future).join();
        
        // Verileri al
        WeatherData api1Data = api1Future.get();
        WeatherData api2Data = api2Future.get();
        
        // Ortalamayı hesapla
        double avgTemperature = (api1Data.getTemperature() + api2Data.getTemperature()) / 2;
        double avgHumidity = (api1Data.getHumidity() + api2Data.getHumidity()) / 2;
        double avgWindSpeed = (api1Data.getWindSpeed() + api2Data.getWindSpeed()) / 2;
        
        return new WeatherData(avgTemperature, avgHumidity, avgWindSpeed);
    }
}
