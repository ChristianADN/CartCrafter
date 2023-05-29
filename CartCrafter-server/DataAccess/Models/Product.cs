using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class Product
{
    public string Id { get; set; } = null!;

    public string ProductName { get; set; } = null!;

    public string TypeId { get; set; } = null!;

    public string? ProductDescription { get; set; }

    public string? BarCode { get; set; }

    public string? BrandId { get; set; }

    public byte[]? Image { get; set; }

    public string? Animal { get; set; }

    public float Weight { get; set; }

    public virtual ICollection<ProductReview> ProductReviews { get; } = new List<ProductReview>();

    public virtual ICollection<ProductShop> ProductShops { get; } = new List<ProductShop>();

    public virtual ICollection<ShoppingListProduct> ShoppingListProducts { get; } = new List<ShoppingListProduct>();

    public virtual ProductType Type { get; set; } = null!;
}
