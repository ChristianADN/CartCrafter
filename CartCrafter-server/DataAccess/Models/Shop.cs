using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class Shop
{
    public string Id { get; set; } = null!;

    public string ShopName { get; set; } = null!;

    public string? ChainId { get; set; }

    public string ShopAddress { get; set; } = null!;

    public double Latitude { get; set; }

    public double Longitude { get; set; }

    public virtual Chain? Chain { get; set; }

    public virtual ICollection<ProductShop> ProductShops { get; } = new List<ProductShop>();
}
