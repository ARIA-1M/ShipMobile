using System;
using System.Collections.Generic;

namespace ShipAPI.Models;

public partial class Location
{
    public int IdLocation { get; set; }

    public string? Title { get; set; }

    public string Position { get; set; } = null!;

    public virtual ICollection<Schedule> Schedules { get; set; } = new List<Schedule>();
}
