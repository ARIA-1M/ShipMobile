using System;
using System.Collections.Generic;

namespace ShipAPI.Models;

public partial class Schedule
{
    public int IdSchedule { get; set; }

    public string Title { get; set; } = null!;

    public DateOnly Date { get; set; }

    public TimeOnly? StartTime { get; set; }

    public TimeOnly? EndTime { get; set; }

    public int IdLocation { get; set; }

    public virtual Location IdLocationNavigation { get; set; } = null!;
}
