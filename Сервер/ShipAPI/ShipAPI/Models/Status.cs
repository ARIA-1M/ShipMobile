using System;
using System.Collections.Generic;

namespace ShipAPI.Models;

public partial class Status
{
    public int IdStatus { get; set; }

    public string Name { get; set; } = null!;

    public string? Description { get; set; }

    public virtual ICollection<Cabin> Cabins { get; set; } = new List<Cabin>();
}
