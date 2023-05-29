using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class ProductType
{
    public string Id { get; set; } = null!;

    public string Name { get; set; } = null!;

    public string? Description { get; set; }

    public string? CategoryId { get; set; }

    public byte[]? Image { get; set; }

    public virtual Category? Category { get; set; }

    public virtual ICollection<Product> Products { get; } = new List<Product>();
}
