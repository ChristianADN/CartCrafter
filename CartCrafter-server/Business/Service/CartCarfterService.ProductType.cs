using Business.Dto;
using DataAccess.Data;
using DataAccess.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Service
{
    public partial class CartCarfterService //ProductType
    {
        public async Task<List<ProductTypeDto>> GetProductTypes()
        {
            return _mapper.Map<List<ProductTypeDto>>(await _context.ProductTypes.ToListAsync());
        }
        public async Task<ProductTypeDto> GetProductType(Guid id)
        {
            return _mapper.Map<ProductTypeDto>(await _context.ProductTypes.FindAsync(id.ToString()));
        }
        public async Task<List<ProductTypeDto>> GetProductTypesByCategory(Guid parentId)
        {
            return _mapper.Map<List<ProductTypeDto>>(await _context.ProductTypes.Where(c => c.CategoryId == parentId.ToString()).ToListAsync());
        }
        public async Task<ProductTypeDto> UpdateProductType(ProductTypeDto productTypeDto)
        {
            var productType = _mapper.Map<ProductType>(productTypeDto);

            try
            {
                _context.Entry(productType).State = EntityState.Modified;
                await _context.SaveChangesAsync();
                return await GetProductType(Guid.Parse(productTypeDto.Id));
            }
            catch (DbUpdateConcurrencyException exception)
            {
                throw exception;
            }
        }
        public async Task<ProductTypeDto> AddProductType(ProductTypeDto productTypeDto)
        {

            productTypeDto.Id = Guid.NewGuid().ToString();
            var productType = _mapper.Map<ProductType>(productTypeDto);

            try
            {
                _context.ProductTypes.Add(productType);
                await _context.SaveChangesAsync();
                return await GetProductType(Guid.Parse(productTypeDto.Id));
            }
            catch (DbUpdateException)
            {
                throw;
            }
        }
    }
}
