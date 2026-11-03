namespace ShipAPI.Models
{
    public class ScheduleDto
    {
        public int IdSchedule { get; set; }
        public string Title { get; set; }
        public DateOnly Date { get; set; }
        public TimeOnly? StartTime { get; set; }
        public TimeOnly? EndTime { get; set; }
        public string LocationTitle { get; set; } 
    }
}