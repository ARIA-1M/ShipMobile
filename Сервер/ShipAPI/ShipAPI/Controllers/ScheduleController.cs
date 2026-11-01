using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ShipAPI.Models;

namespace ShipAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    [Authorize(Roles = "ADMIN")]
    public class ScheduleController : ControllerBase
    {
        private readonly PostgresContext _context;

        // Подключаем базу данных
        public ScheduleController(PostgresContext context)
        {
            _context = context;
        }

        //Обработка ошибок
        private object ErrorResponse(string message)
        {
            return new { error = "Ошибка запроса", message = message };
        }

        // GET: api/schedule
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ScheduleDto>>> GetSchedules()
        {
            var today = DateOnly.FromDateTime(DateTime.Now);

            return await _context.Schedules
                .Include(s => s.IdLocationNavigation)
                .Where(s => s.Date == today)
                .OrderBy(s => s.StartTime)
                .Select(s => new ScheduleDto
                {
                    IdSchedule = s.IdSchedule,
                    Title = s.Title,
                    Date = s.Date,
                    StartTime = s.StartTime,
                    EndTime = s.EndTime,
                    LocationTitle = s.IdLocationNavigation.Title,
                   
                })
                .ToListAsync();
        }

        // POST: api/schedule
        [HttpPost]
        public async Task<ActionResult<ScheduleDto>> CreateSchedule(ScheduleDto newSchedule)
        {
            var location = await _context.Locations
                .FirstOrDefaultAsync(l => l.Title == newSchedule.LocationTitle);

            if (location == null)
            {
                return BadRequest(ErrorResponse("Указанная локация не найдена"));
            }

            var schedule = new Schedule 
            {
                Title = newSchedule.Title,
                Date = newSchedule.Date,
                StartTime = newSchedule.StartTime,
                EndTime = newSchedule.EndTime,
                IdLocation = location.IdLocation
            };
            _context.Schedules.Add(schedule);
            await _context.SaveChangesAsync();

            newSchedule.IdSchedule = schedule.IdSchedule;
            return Ok(newSchedule);
        }

        // DELETE: api/schedule/{id}
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteSchedule(int id)
        {
            var schedule = await _context.Schedules.FindAsync(id);
            if (schedule == null)
            {
                return NotFound(ErrorResponse("Мероприятие с таким ID не найдено"));
            }

            _context.Schedules.Remove(schedule);
            await _context.SaveChangesAsync();

            return Ok(new { message = "Мероприятие успешно удалено" });
        }


        // PUT: api/schedule/{id}
        [HttpPut("{id}")]
        public async Task<IActionResult> UpdateSchedule(int id, ScheduleDto updatedSchedule)
        {
            var schedule = await _context.Schedules.FindAsync(id);
            if (schedule == null)
            {
                return NotFound(ErrorResponse("Мероприятие с таким ID не найдено"));
            }

            var location = await _context.Locations
                .FirstOrDefaultAsync(l => l.Title == updatedSchedule.LocationTitle);
            if (location == null)
            {
                return BadRequest(ErrorResponse("Указанная локация не найдена"));
            }

          
            schedule.Title = updatedSchedule.Title;
            schedule.Date = updatedSchedule.Date;
            schedule.StartTime = updatedSchedule.StartTime;
            schedule.EndTime = updatedSchedule.EndTime;
            schedule.IdLocation = location.IdLocation; 
            try
            {
                await _context.SaveChangesAsync();
            }
            catch (DbUpdateConcurrencyException)
            {
                //Если кто-то удалил мероприятие, пока мы его редактировали
                if (!_context.Schedules.Any(e => e.IdSchedule == id))
                {
                    return NotFound(ErrorResponse("Мероприятие было удалено"));
                }
                else
                {
                    throw;
                }
            }
            return NoContent();
        }
    }
}
