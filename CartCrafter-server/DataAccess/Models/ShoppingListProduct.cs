using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class ShoppingListProduct
{
    public string ShoppingListId { get; set; } = null!;

    public string ProductId { get; set; } = null!;

    public int Quantity { get; set; }

    public bool AddedToCart { get; set; }

    public virtual Product Product { get; set; } = null!;
    public virtual Shop Shop { get; set; } = null!;

    public virtual ShoppingList ShoppingList { get; set; } = null!;
}
