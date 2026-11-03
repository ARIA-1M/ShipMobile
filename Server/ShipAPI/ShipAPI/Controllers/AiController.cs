using LD.Sber.GigaChatSDK;
using LD.Sber.GigaChatSDK.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.AspNetCore.RateLimiting;
using ShipAPI.Models;

namespace ShipAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [EnableRateLimiting("api")]
    public class AiController : ControllerBase
    {
        private readonly string _authKey;
        private readonly WeatherService _weatherService;

        public AiController(IConfiguration configuration, WeatherService weatherService)
        {
            _authKey = configuration["GigaChat:AuthorizationKey"]
                       ?? throw new InvalidOperationException("Ключ GigaChat не найден");
            _weatherService = weatherService;
        }

        [HttpPost("chat")]
        public async Task<IActionResult> ChatWithAi([FromBody] ChatRequest request)
        {
            if (request.Messages == null || !request.Messages.Any())
                return BadRequest(new { error = "Пустой запрос" });

            try
            {
                // 1. Берем город и расписание из запроса
                string currentCity = request.Context?.City ?? "Нижний Новгород";
                string scheduleText = request.Context?.Schedule ?? "Расписание пока недоступно";

                // 2. Получаем погоду
                string weatherInfo = await _weatherService.GetWeatherAsync(currentCity);

                // 3. Инициализация GigaChat
                var httpService = new HttpService(ignoreTLS: true);
                var tokenService = new TokenService(httpService, _authKey, isCommercial: false);
                var chat = new GigaChat(tokenService, httpService, saveImage: false);
                await chat.CreateTokenAsync();

                // 4. Формируем запрос
                var query = new MessageQuery();

                // ДОБАВЛЯЕМ РАСПИСАНИЕ В СИСТЕМНЫЙ ПРОМПТ
                string systemPrompt = $"Ты — вежливый ИИ-консьерж на теплоходе. " +
                                      $"Сейчас мы в городе {currentCity}. " +
                                      $"Погода: {weatherInfo}. " +
                                      $"Вот расписание мероприятий на сегодня: {scheduleText}. " +
                                      $"Если пользователь спрашивает 'что сейчас идет' или 'куда сходить', используй это расписание. " +
                                      $"Отвечай кратко и по делу.";

                query.Messages.Add(new MessageContent("system", systemPrompt));

                foreach (var msg in request.Messages)
                {
                    query.Messages.Add(new MessageContent(msg.Role, msg.Content));
                }

                // 5. Отправляем запрос
                var response = await chat.CompletionsAsync(query);
                string aiAnswer = response.Choices.LastOrDefault().Message.Content;

                return Ok(new { role = "assistant", content = aiAnswer });
            }
            catch (Exception ex)
            {
                return StatusCode(500, new { error = "Ошибка ИИ", message = ex.Message });
            }
        }

        [HttpGet("test-weather/{city}")]
        public async Task<IActionResult> TestWeather(string city)
        {
            var result = await _weatherService.GetWeatherAsync(city);
            return Ok(new { city = city, weather = result });
        }
    }
}