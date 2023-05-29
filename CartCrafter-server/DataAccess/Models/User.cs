using Microsoft.AspNetCore.Identity;
using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class User:IdentityUser
{
    public virtual ICollection<ProductReview> ProductReviews { get; } = new List<ProductReview>();

    public virtual ICollection<ShoppingList> ShoppingLists { get; } = new List<ShoppingList>();
}
