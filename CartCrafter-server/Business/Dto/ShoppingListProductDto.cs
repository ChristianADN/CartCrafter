using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class ShoppingListProductDto
{
    public string ShoppingListId { get; set; } = null!;

    public string ProductId { get; set; } = null!;

    public int Quantity { get; set; }

    public bool AddedToCart { get; set; }
}
