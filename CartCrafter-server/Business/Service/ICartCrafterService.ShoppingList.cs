using Business.Dto;
using DataAccess.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Service
{
    public partial interface ICartCrafterService
    {
        Task<List<ShoppingListDto>> GetShoppingLists();
        Task<ShoppingListDto> GetShoppingList(Guid id);
        Task<List<ShoppingListDto>> GetShoppingListsByUser(Guid userId);
        Task<ShoppingListDto> UpdateShoppingList(ShoppingListDto shoppingListDto);
        Task<ShoppingListDto> AddShoppingList(ShoppingListDto shoppingListDto);
        Task RemoveShoppingList(ShoppingListDto shoppingListDto);
    }
}
