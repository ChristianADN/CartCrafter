using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class Category
{
    public string Id { get; set; } = null!;

    public string Name { get; set; } = null!;

    public string? Description { get; set; }

    public string? ParentId { get; set; }

    public byte[]? Image { get; set; }

    public virtual ICollection<Category> InverseParent { get; } = new List<Category>();

    public virtual Category? Parent { get; set; }

    public virtual ICollection<ProductType> ProductTypes { get; } = new List<ProductType>();
}
