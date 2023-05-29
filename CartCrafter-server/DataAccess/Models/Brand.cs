using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class Brand
{
    public string Id { get; set; } = null!;

    public string BrandName { get; set; } = null!;

    public byte[]? Logo { get; set; }
}
