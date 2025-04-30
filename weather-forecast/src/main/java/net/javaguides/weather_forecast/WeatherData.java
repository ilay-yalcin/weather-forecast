package net.javaguides.weather_forecast;

public class WeatherData {
    private double temperature;
    private double humidity;
    private double windSpeed;

    
      
        public WeatherData(double temperature, double humidity, double windSpeed) {
            this.temperature = temperature;
            this.humidity = humidity;
            this.windSpeed = windSpeed;
            
        }
    

    // Getters ve Setters

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public double getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(double windSpeed) {
        this.windSpeed = windSpeed;
    }
   
}
