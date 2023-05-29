using Business.Dto;
using DataAccess.Models;
using System;
using System.Collections.Generic;
using System.Threading.Tasks;

namespace Business.Service
{
    public partial interface ICartCrafterService
    {
        Task<List<ShoppingListProductDto>> GetShoppingListProducts();
        Task<ShoppingListProductDto> GetShoppingListProduct(Guid ProductId, Guid ShoppingListId);
        Task<List<ShoppingListProductDto>> GetShoppingListProductsByShoppingList(Guid ShoppingListId);
        Task<ShoppingListProductDto> UpdateShoppingListProduct(ShoppingListProductDto ShoppingListProductDto);
        Task<ShoppingListProductDto> AddShoppingListProduct(ShoppingListProductDto shoppingListProductDto);
        Task<bool> RemoveShoppingListProduct(ShoppingListProductDto ShoppingListProductDto);
    }
}
