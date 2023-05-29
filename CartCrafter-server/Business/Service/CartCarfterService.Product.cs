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
    public partial class CartCarfterService //Products
    {
        public async Task<List<ProductDto>> GetProducts()
        {
            return _mapper.Map<List<ProductDto>>(await _context.Products.ToListAsync());
        }
        public async Task<ProductDto> GetProduct(Guid id)
        {
            return _mapper.Map<ProductDto>(await _context.Products.FindAsync(id.ToString()));
        }
        public async Task<List<ProductDto>> GetProductsByType(Guid parentId)
        {
            return _mapper.Map<List<ProductDto>>(await _context.Products.Where(c => c.TypeId == parentId.ToString()).ToListAsync());
        }
        public async Task<ProductDto> UpdateProduct(ProductDto productDto)
        {
            var product = _mapper.Map<Product>(productDto);
            _context.Entry(product).State = EntityState.Modified;

            try
            {
                await _context.SaveChangesAsync();
                return await GetProduct(Guid.Parse(productDto.Id));
            }
            catch (DbUpdateConcurrencyException exception)
            {
                throw exception;
            }
        }
        public async Task<ProductDto> AddProduct(ProductDto productDto)
        {

            productDto.Id = Guid.NewGuid().ToString();
            var product = _mapper.Map<Product>(productDto);


            try
            {
                _context.Products.Add(product);
                await _context.SaveChangesAsync();
                return await GetProduct(Guid.Parse(productDto.Id));
            }
            catch (DbUpdateException)
            {
                    throw;
            }
        }
    }
}
