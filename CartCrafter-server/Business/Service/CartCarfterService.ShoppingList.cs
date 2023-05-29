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
    public partial class CartCarfterService //ShoppingList
    {
        public async Task<List<ShoppingListDto>> GetShoppingLists()
        {
            return _mapper.Map<List<ShoppingListDto>>(await _context.ShoppingLists.ToListAsync());
        }
        public async Task<ShoppingListDto> GetShoppingList(Guid id)
        {
            return _mapper.Map<ShoppingListDto>(await _context.ShoppingLists.FindAsync(id.ToString()));
        }
        public async Task<List<ShoppingListDto>> GetShoppingListsByUser(Guid userId)
        {
            return _mapper.Map<List<ShoppingListDto>>(await _context.ShoppingLists.Where(c => c.UserId == userId.ToString()).ToListAsync());
        }
        public async Task<ShoppingListDto> UpdateShoppingList(ShoppingListDto ShoppingListDto)
        {
            var ShoppingList = _mapper.Map<ShoppingList>(ShoppingListDto);

            try
            {
                _context.Entry(ShoppingList).State = EntityState.Modified;
                await _context.SaveChangesAsync();
                return await GetShoppingList(Guid.Parse(ShoppingListDto.Id));
            }
            catch (DbUpdateConcurrencyException exception)
            {
                throw exception;
            }
        }
        public async Task<ShoppingListDto> AddShoppingList(ShoppingListDto shoppingListDto)
        {

            shoppingListDto.Id = Guid.NewGuid().ToString();
            var shoppingList = _mapper.Map<ShoppingList>(shoppingListDto);

            try
            {
                _context.ShoppingLists.Add(shoppingList);
                await _context.SaveChangesAsync();
                return await GetShoppingList(Guid.Parse(shoppingListDto.Id));
            }
            catch (DbUpdateException)
            {
                throw;
            }
        }
        public async Task RemoveShoppingList(ShoppingListDto shoppingListDto)
        {
            var shoppingList = await _context.ShoppingLists.FindAsync(shoppingListDto.Id);

            if (shoppingList != null)
            {
                _context.ShoppingLists.Remove(shoppingList);
                await _context.SaveChangesAsync();
            }
        }

    }
}
