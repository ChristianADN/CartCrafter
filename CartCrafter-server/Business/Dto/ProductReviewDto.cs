using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Dto
{
    public class ProductReviewDto
    {
        public string? Id { get; set; } = null!;

        public string ProductId { get; set; } = null!;

        public string UserId { get; set; } = null!;

        public string UserName { get; set; }

        public int Rating { get; set; }

        public byte[]? Image { get; set; }

        public string? Text { get; set; }
    }
}
