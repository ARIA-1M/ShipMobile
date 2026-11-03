using Microsoft.AspNetCore.Mvc;
using System.Globalization;
using System.Text.Json;

namespace ShipAPI.Controllers
{
    public class WeatherService
    {
        private readonly HttpClient _httpClient;
        private readonly string _apiKey;

        public WeatherService(HttpClient httpClient, IConfiguration config)
        {
            _httpClient = httpClient;
            _apiKey = config["OpenWeather:ApiKey"] ?? "";
        }

        public async Task<string> GetWeatherAsync(string city)
        {
            try
            {
                // OpenWeatherMap лучше ищет на английском, но для простоты попробуем русский
                var url = $"https://api.openweathermap.org/data/2.5/weather?q={Uri.EscapeDataString(city)}&appid={_apiKey}&units=metric&lang=ru";
                var response = await _httpClient.GetStringAsync(url);

                using var doc = JsonDocument.Parse(response);
                var temp = doc.RootElement.GetProperty("main").GetProperty("temp").GetDouble();
                var humidity = doc.RootElement.GetProperty("main").GetProperty("humidity").GetInt32();
                var desc = doc.RootElement.GetProperty("weather")[0].GetProperty("description").GetString();

                return $"{city}: {temp}°C, влажность {humidity}%, {desc}";
            }
            catch (Exception ex)
            {
                return $"Ошибка получения погоды: {ex.Message}";
            }
        }
    }
}
