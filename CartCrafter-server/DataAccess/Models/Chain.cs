using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class Chain
{
    public string Id { get; set; } = null!;

    public string ChainName { get; set; } = null!;

    public virtual ICollection<Shop> Shops { get; } = new List<Shop>();
}
