using System;
using System.Collections.Generic;

namespace DataAccess.Models;

public partial class ProductReview
{
    public string Id { get; set; } = null!;

    public string ProductId { get; set; } = null!;

    public string UserId { get; set; } = null!;

    public int Rating { get; set; }

    public byte[]? Image { get; set; }

    public string? Text { get; set; }

    public virtual Product Product { get; set; } = null!;

    public virtual User User { get; set; } = null!;
}
