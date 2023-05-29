using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Dto
{
    public class ShopDto
    {
        public string Id { get; set; } = null!;

        public string ShopName { get; set; } = null!;

        public string? ChainId { get; set; }

        public string ShopAddress { get; set; } = null!;

        public double Latitude { get; set; }

        public double Longitude { get; set; }
    }
}
