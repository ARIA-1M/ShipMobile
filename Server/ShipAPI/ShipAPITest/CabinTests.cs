using NUnit.Framework;
using Microsoft.EntityFrameworkCore;
using ShipAPI.Controllers;
using ShipAPI.Models;
using Microsoft.AspNetCore.Mvc;
using System;
using System.Linq;
using System.Threading.Tasks;
using System.Collections.Generic;

namespace ShipAPITest
{
    [TestFixture]
    public class CabinTests
    {
        private PostgresContext _context;
        private CabinController _controller;

        [SetUp]
        public void Setup()
        {
            var options = new DbContextOptionsBuilder<PostgresContext>()
                .UseInMemoryDatabase(databaseName: Guid.NewGuid().ToString())
                .Options;

            _context = new PostgresContext(options);

            _context.Statuses.AddRange(
                new Status { IdStatus = 1, Name = "убраться" },
                new Status { IdStatus = 2, Name = "не беспокоить" },
                new Status { IdStatus = 3, Name = "sos" },
                new Status { IdStatus = 4, Name = "нет связи" }
            );

            _context.Cabins.AddRange(
                new Cabin { IdCabin = 1, Number = 101, IdStatus = 1 },
                new Cabin { IdCabin = 2, Number = 102, IdStatus = 2 },
                new Cabin { IdCabin = 3, Number = 201, IdStatus = 3 },
                new Cabin { IdCabin = 4, Number = 202, IdStatus = 4 }
            );

            _context.SaveChanges();
            _controller = new CabinController(_context);
        }

        [TearDown]
        public void TearDown()
        {
            _context.Dispose();
        }

        // Пагинация
        [Test]
        public async Task GetCabins_ReturnsOkWithPagination()
        {
            var result = await _controller.GetCabins(1, 2);

            Assert.IsInstanceOf<OkObjectResult>(result);

            var okResult = result as OkObjectResult;
            Assert.AreEqual(200, okResult.StatusCode);
        }

        // Проверка логики БД
        [Test]
        public async Task GetCabins_ExcludesNonTargetStatuses()
        {
            var result = await _controller.GetCabins(1, 20);

            Assert.IsInstanceOf<OkObjectResult>(result);

            var allCabinsInDb = await _context.Cabins.CountAsync();
            Assert.AreEqual(4, allCabinsInDb);

            var excludedCabin = await _context.Cabins.Include(c => c.IdStatusNavigation)
                .FirstOrDefaultAsync(c => c.Number == 202);
            Assert.IsNotNull(excludedCabin);
            Assert.AreEqual("нет связи", excludedCabin.IdStatusNavigation.Name);
        }

        // Получение только тревог SOS
        [Test]
        public async Task GetAlarms_ReturnsOnlySosCabins()
        {
            var result = await _controller.GetAlarms();

            var okResult = result.Result as OkObjectResult;
            Assert.IsNotNull(okResult);
            Assert.AreEqual(200, okResult.StatusCode);

            var returnedList = okResult.Value as IEnumerable<CabinDto>;
            Assert.IsNotNull(returnedList);

            Assert.AreEqual(1, returnedList.Count());
            Assert.AreEqual(201, returnedList.First().Number);
            Assert.AreEqual("sos", returnedList.First().StatusName);
        }
    }
}