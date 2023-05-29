using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class ProductShop
{
    public string Id { get; set; } = null!;

    public string ProductId { get; set; } = null!;

    public string ShopId { get; set; } = null!;

    public decimal Price { get; set; }

    public DateTime DateAdded { get; set; }

    public virtual Product Product { get; set; } = null!;

    public virtual Shop Shop { get; set; } = null!;
}
