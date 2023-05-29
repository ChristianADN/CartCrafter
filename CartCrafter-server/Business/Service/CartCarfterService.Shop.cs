using Business.Dto;
using DataAccess.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Service
{
    public partial class CartCarfterService //Shops
    {
        public async Task<List<ShopDto>> GetShops() {
            var shops = await _context.Shops.ToListAsync();
            var shopsDto =_mapper.Map<List<ShopDto>>(shops);
            return shopsDto;
        }
        public async Task<ShopDto> GetShop(Guid id)
        {
            var shop = await _context.Shops.FindAsync(id.ToString());
            var shopDto = _mapper.Map<ShopDto>(shop);
            return shopDto;
        }
    }
}
