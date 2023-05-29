using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public class ShoppingListDto
{
    public string Id { get; set; } = null!;

    public string UserId { get; set; } = null!;

    public DateTime DateCreated { get; set; }

    public bool Archived { get; set; }
}
