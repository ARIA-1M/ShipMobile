using System;
using System.Collections.Generic;

namespace ShipAPI.Models;

public partial class Cabin
{
    public int IdCabin { get; set; }

    public int IdStatus { get; set; }

    public int Number { get; set; }

    public bool IsDay { get; set; }

    public int Deck { get; set; }

    public virtual Status IdStatusNavigation { get; set; } = null!;
}
