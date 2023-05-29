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
    public partial class CartCarfterService
    {
        public async Task<List<ProductShopDto>> GetProductShops()
        {
            return _mapper.Map<List<ProductShopDto>>(await _context.ProductShops.ToListAsync());
        }

        public async Task<List<ProductShopDto>> GetProductShopsByProduct(Guid id)
        {
            return _mapper.Map<List<ProductShopDto>>(_context.ProductShops.Where(ps => ps.ProductId == id.ToString()));
        }

        public async Task<ProductShopDto> AddProductShop(ProductShopDto productShopDto)
        {
            productShopDto.Id = Guid.NewGuid().ToString();
            var productShop = _mapper.Map<ProductShop>(productShopDto);

            try
            {
                _context.ProductShops.Add(productShop);
                await _context.SaveChangesAsync();
                return _mapper.Map<ProductShopDto>(productShop);
            }
            catch (DbUpdateException)
            {
                throw;
            }
        }
    }
}
