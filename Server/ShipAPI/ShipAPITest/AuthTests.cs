using NUnit.Framework;
using ShipAPI.Controllers;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Configuration;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace ShipAPITest
{
    [TestFixture]
    public class AuthTests
    {
        private AuthController _controller;

        [SetUp]
        public void Setup()
        {
            var inMemorySettings = new Dictionary<string, string> {
                {"JwtSettings:SecretKey", "SuperSecretKeyForCruiseMindProject1234567890!"},
                {"JwtSettings:Issuer", "CruiseMindAPI"},
                {"JwtSettings:Audience", "CruiseMindClient"}
            };

            IConfiguration configuration = new ConfigurationBuilder()
                .AddInMemoryCollection(inMemorySettings)
                .Build();

            _controller = new AuthController(configuration);
        }

        // Тест на успешную авторизацию администратора
        [Test]
        public void Login_AdminPassword_ReturnsOkWithToken()
        {
            var request = new LoginRequest { Password = "admin123" };

            var result = _controller.Login(request) as OkObjectResult;

            Assert.IsNotNull(result);
            Assert.AreEqual(200, result.StatusCode);
            Assert.IsNotNull(result.Value);
            var valueString = result.Value.ToString();
            Assert.IsTrue(valueString.Contains("ADMIN"));
        }

        // Тест на неверный пароль
        [Test]
        public void Login_InvalidPassword_ReturnsUnauthorized()
        {
            var request = new LoginRequest { Password = "wrongpassword" };

            var result = _controller.Login(request);

            var unauthorizedResult = result as UnauthorizedObjectResult;
            Assert.IsNotNull(unauthorizedResult);
            Assert.AreEqual(401, unauthorizedResult.StatusCode);
        }
    }
}