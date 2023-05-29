using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class ShoppingList
{
    public string Id { get; set; } = null!;

    public string Name { get; set; } = null!;

    public string UserId { get; set; } = null!;

    public DateTime DateCreated { get; set; }

    public bool Archived { get; set; }

    public virtual ICollection<ShoppingListProduct> ShoppingListProducts { get; } = new List<ShoppingListProduct>();

    public virtual User User { get; set; } = null!;
}
