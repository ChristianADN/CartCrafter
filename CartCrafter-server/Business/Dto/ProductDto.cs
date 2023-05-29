using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Dto
{
    public class ProductDto
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

    }
}
