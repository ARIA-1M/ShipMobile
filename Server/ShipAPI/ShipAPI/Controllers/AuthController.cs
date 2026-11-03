using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Security.Claims;
using Microsoft.IdentityModel.Tokens;
using System.IdentityModel.Tokens.Jwt;
using System.Text;

namespace ShipAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class AuthController : ControllerBase
    {
        private readonly IConfiguration _config;

        public AuthController(IConfiguration config)
        {
            _config = config;
        }

        // POST: api/auth/login
        [HttpPost("login")]
        public IActionResult Login([FromBody] LoginRequest request)
        {
            // Для теста мы просто жестко зададим пароли.
            // В реальной жизни они берутся из базы данных.
            string role = "";
            if (request.Password == "admin123") role = "ADMIN";
            else if (request.Password == "cleaner123") role = "CLEANER";
            else return Unauthorized(new { message = "Неверный пароль" });

            // Генерируем токен
            var token = GenerateJwtToken(role);
            return Ok(new { token = token, role = role });
        }

        private string GenerateJwtToken(string role)
        {
            var key = new SymmetricSecurityKey(Encoding.UTF8.GetBytes(_config["JwtSettings:SecretKey"]));
            var credentials = new SigningCredentials(key, SecurityAlgorithms.HmacSha256);

            // Здесь мы зашиваем роль в токен
            var claims = new[] { new Claim(ClaimTypes.Role, role) };

            var token = new JwtSecurityToken(
                issuer: _config["JwtSettings:Issuer"],
                audience: _config["JwtSettings:Audience"],
                claims: claims,
                expires: DateTime.UtcNow.AddHours(8), // Токен живет 8 часов
                signingCredentials: credentials);

            return new JwtSecurityTokenHandler().WriteToken(token);
        }
    }

    public class LoginRequest
    {
        public string Password { get; set; }
    }

}
