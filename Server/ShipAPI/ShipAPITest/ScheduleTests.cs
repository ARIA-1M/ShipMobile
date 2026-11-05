using NUnit.Framework;
using Microsoft.EntityFrameworkCore;
using ShipAPI.Controllers;
using ShipAPI.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;

namespace ShipAPITest
{
    [TestFixture]
    public class ScheduleTests
    {
        private PostgresContext _context;
        private ScheduleController _controller;

        [SetUp]
        public void Setup()
        {
            
            var options = new DbContextOptionsBuilder<PostgresContext>()
                .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString()) 
                .Options;

            _context = new PostgresContext(options);
            _context.Locations.Add(new Location { IdLocation = 1, Title = "Ресторан «Столица»", Position = "Палуба 2" });
            _context.SaveChanges();
            _controller = new ScheduleController(_context);
        }

        [TearDown]
        public void TearDown()
        {
            _context.Dispose();
        }

        //Тест на чтение данных о расписании на день
        [Test]
        public async Task GetSchedules_ReturnsOk()
        {
            var result = await _controller.GetSchedules();
            Assert.IsNotNull(result);
            Assert.IsInstanceOf<ActionResult<IEnumerable<ScheduleDto>>>(result);
        }

        // Тест на создания мероприятия
        [Test]
        public async Task CreateSchedule_ValidData_ReturnsOk()
        {
            
            var newSchedule = new ScheduleDto
            {
                Title = "Новое мероприятие",
                Date = DateOnly.FromDateTime(DateTime.Now),
                StartTime = new TimeOnly(12, 0),
                EndTime = new TimeOnly(13, 0),
                LocationTitle = "Ресторан «Столица»" 
            };
            var result = await _controller.CreateSchedule(newSchedule);

            Assert.IsNotNull(result);
            Assert.AreEqual(1, _context.Schedules.Count()); 
        }

        // Тест на создания мероприятия с несуществующей локацией
        [Test]
        public async Task CreateSchedule_InvalidLocation_ReturnsBadRequest()
        {
            var newSchedule = new ScheduleDto
            {
                Title = "Тест",
                LocationTitle = "Марс" 
            };

            var result = await _controller.CreateSchedule(newSchedule);

            Assert.IsInstanceOf<BadRequestObjectResult>(result.Result);
        }

        // Тест на удаление мероприятия
        [Test]
        public async Task DeleteSchedule_ExistingId_ReturnsOk()
        {
            _context.Schedules.Add(new Schedule { IdSchedule = 1, Title = "Тест", IdLocation = 1 });
            await _context.SaveChangesAsync();

            var result = await _controller.DeleteSchedule(1);
            Assert.IsInstanceOf<OkObjectResult>(result);
            Assert.AreEqual(0, _context.Schedules.Count()); 
        }

        // Тест на удаление мероприятия с несуществующим ID
        [Test]
        public async Task DeleteSchedule_NotFound_ReturnsNotFound()
        {
            var result = await _controller.DeleteSchedule(999); 

            Assert.IsInstanceOf<NotFoundObjectResult>(result);
        }

        // Тест на обновление мероприятия
        [Test]
        public async Task UpdateSchedule_ValidData_ReturnsNoContent()
        {
            _context.Schedules.Add(new Schedule { IdSchedule = 1, Title = "Старое", IdLocation = 1 });
            await _context.SaveChangesAsync();

            var updated = new ScheduleDto
            {
                Title = "Новое",
                Date = DateOnly.FromDateTime(DateTime.Now),
                StartTime = new TimeOnly(10, 0),
                EndTime = new TimeOnly(11, 0),
                LocationTitle = "Ресторан «Столица»"
            };

            var result = await _controller.UpdateSchedule(1, updated);

            Assert.IsInstanceOf<NoContentResult>(result);

            var scheduleInDb = await _context.Schedules.FindAsync(1);
            Assert.AreEqual("Новое", scheduleInDb.Title);
        }

        //Тест на обновление мероприятия, добавление несуществующей локации
        [Test]
        public async Task UpdateSchedule_InvalidLocation_ReturnsBadRequest()
        {
            _context.Schedules.Add(new Schedule { IdSchedule = 1, Title = "Старое", IdLocation = 1 });
            await _context.SaveChangesAsync();

            var updated = new ScheduleDto
            {
                Title = "Новое",
                LocationTitle = "Марс"
            };

            var result = await _controller.UpdateSchedule(1, updated);

            Assert.IsInstanceOf<BadRequestObjectResult>(result);
        }
    }
}