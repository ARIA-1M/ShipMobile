using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ShipAPI.Models;

namespace ShipAPI.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class CabinController : ControllerBase
    {
        private readonly PostgresContext _context;

        public CabinController(PostgresContext context)
        {
            _context = context;
        }

        // GET: api/cabin
        [HttpGet] 
        public async Task<ActionResult> GetCabins([FromQuery] int page = 1, [FromQuery] int limit = 20)
        {
            var targetStatuses = new[] { "убраться", "не беспокоить", "sos" };

            var query = _context.Cabins
                .Include(c => c.IdStatusNavigation)
                .Where(c => targetStatuses.Contains(c.IdStatusNavigation.Name));

            var totalCount = await query.CountAsync();
            var cabins = await query
                .OrderBy(c => c.Number)
                .Skip((page - 1) * limit)
                .Take(limit)
                .Select(c => new CabinDto
                {
                    IdCabin = c.IdCabin,
                    Number = c.Number,
                    StatusName = c.IdStatusNavigation.Name
                })
                .ToListAsync();

            return Ok(new
            {
                total = totalCount,
                page = page,
                limit = limit,
                data = cabins
            });
        }

        // GET: api/cabin/alarms
        [HttpGet("alarms")]
        public async Task<ActionResult<IEnumerable<CabinDto>>> GetAlarms()
        {
            var alarmStatuses = new[] { "sos" };

            var alarms = await _context.Cabins
                .Include(c => c.IdStatusNavigation)
                .Where(c => alarmStatuses.Contains(c.IdStatusNavigation.Name))
                .OrderBy(c => c.Number)
                .Select(c => new CabinDto
                {
                    IdCabin = c.IdCabin,
                    Number = c.Number,
                    StatusName = c.IdStatusNavigation.Name
                })
                .ToListAsync();

            return Ok(alarms);
        }

    }
}
