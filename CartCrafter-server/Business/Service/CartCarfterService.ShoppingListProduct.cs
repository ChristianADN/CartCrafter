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
    public partial class CartCarfterService //ShoppingListProduct
    {
        public async Task<List<ShoppingListProductDto>> GetShoppingListProducts()
        {
            return _mapper.Map<List<ShoppingListProductDto>>(await _context.ShoppingListProducts.ToListAsync());
        }
        public async Task<ShoppingListProductDto> GetShoppingListProduct(Guid ProductId, Guid ShoppingListId)
        {
            return _mapper.Map<ShoppingListProductDto>(await _context.ShoppingListProducts.Where(c => c.ShoppingListId == ShoppingListId.ToString()&&c.ProductId==ProductId.ToString()).ToListAsync());
        }
        public async Task<List<ShoppingListProductDto>> GetShoppingListProductsByShoppingList(Guid ShoppingListId)
        {
            return _mapper.Map<List<ShoppingListProductDto>>(await _context.ShoppingListProducts.Where(c => c.ShoppingListId == ShoppingListId.ToString()).ToListAsync());
        }
        public async Task<ShoppingListProductDto> UpdateShoppingListProduct(ShoppingListProductDto ShoppingListProductDto)
        {
            var ShoppingListProduct = _mapper.Map<ShoppingListProductDto>(ShoppingListProductDto);
            try
            {
                _context.Entry(ShoppingListProduct).State = EntityState.Modified;
                await _context.SaveChangesAsync();
                return await GetShoppingListProduct(Guid.Parse(ShoppingListProductDto.ProductId), Guid.Parse(ShoppingListProductDto.ShoppingListId));
            }
            catch (DbUpdateConcurrencyException exception)
            {
                throw exception;
            }
        }
        public async Task<ShoppingListProductDto> AddShoppingListProduct(ShoppingListProductDto shoppingListProductDto)
        {
            var shoppingListProduct = _mapper.Map<ShoppingListProduct>(shoppingListProductDto);

            try
            {
                _context.ShoppingListProducts.Add(shoppingListProduct);
                await _context.SaveChangesAsync();
                return await GetShoppingListProduct(Guid.Parse(shoppingListProductDto.ProductId), Guid.Parse(shoppingListProductDto.ShoppingListId));
            }
            catch (DbUpdateException)
            {
                throw;
            }
        }
        public async Task<bool> RemoveShoppingListProduct(ShoppingListProductDto ShoppingListProductDto)
        {

            var ShoppingListProduct = _mapper.Map<ShoppingListProduct>(ShoppingListProductDto);

            try
            {
                _context.ShoppingListProducts.Remove(ShoppingListProduct);
                await _context.SaveChangesAsync();
                return true;
            }
            catch (DbUpdateException)
            {
                throw;
            }
        }
    }
}
