using DataAccess.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Dto
{
    public class ProductShopDto
    {
        public string Id { get; set; } = null!;

        public string ProductId { get; set; } = null!;

        public string ShopId { get; set; } = null!;

        public decimal Price { get; set; }

        public DateTime DateAdded { get; set; }
    }
}
