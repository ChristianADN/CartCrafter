using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using DataAccess.Models;
using Business.Service;
using Business.Dto;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using ApiRest.Builders;
using ApiRest.ViewModels;

namespace ApiRest.Controllers
{
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/[controller]")]
    [ApiController]
    public class ShoppingListsController : ControllerBase
    {
        private readonly ICartCrafterService _service;
        private readonly UserManager<User> _userManager;

        public ShoppingListsController(ICartCrafterService service, UserManager<User> userManager)
        {
            _service = service;
            _userManager = userManager;
        }

        // GET: api/ShoppingLists
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ShoppingListViewModel>>> GetShoppingLists()
        {
            var user = await _userManager.FindByNameAsync(User.Identity.Name);
            var shoppingLists = await _service.GetShoppingListsByUser(Guid.Parse(user.Id));
            var shoppingListViewModels = new List<ShoppingListViewModel>();

            foreach (var shoppingList in shoppingLists)
            {
                var shoppingListViewModel = await ViewModelBuilder.ConvertToShoppingListViewModel(shoppingList, _service);
                shoppingListViewModels.Add(shoppingListViewModel);
            }

            return shoppingListViewModels;
        }

        // GET: api/ShoppingLists/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ShoppingListViewModel>> GetShoppingList(string id)
        {
            var shoppingList = await _service.GetShoppingList(Guid.Parse(id));

            if (shoppingList == null)
            {
                return NotFound();
            }

            var shoppingListViewModel = await ViewModelBuilder.ConvertToShoppingListViewModel(shoppingList, _service);

            return shoppingListViewModel;
        }

        // PUT: api/ShoppingLists/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutShoppingList(string id, ShoppingListViewModel shoppingListViewModel)
        {
            if (id != shoppingListViewModel.Id)
            {
                return BadRequest();
            }

            var shoppingList = await _service.GetShoppingList(Guid.Parse(id));

            if (shoppingList == null)
            {
                return NotFound();
            }

            try
            {
                shoppingList.Archived = shoppingListViewModel.Archived;
                await _service.UpdateShoppingList(shoppingList);
            }
            catch (DbUpdateConcurrencyException)
            {
                return NotFound();
            }

            return NoContent();
        }

        // POST: api/ShoppingLists
        [HttpPost]
        public async Task<ActionResult<ShoppingListViewModel>> PostShoppingList(ShoppingListViewModel shoppingListViewModel)
        {
            var user = await _userManager.FindByNameAsync(User.Identity.Name);
            var shoppingList = new ShoppingListDto
            {
                UserId = user.Id,
                DateCreated = DateTime.Now,
                Archived = false
            };

            try
            {
                await _service.AddShoppingList(shoppingList);
            }
            catch (DbUpdateException)
            {
                return Conflict();
            }

            shoppingListViewModel.Id = shoppingList.Id;
            shoppingListViewModel.UserId = shoppingList.UserId;
            shoppingListViewModel.DateCreated = shoppingList.DateCreated;
            shoppingListViewModel.Archived = shoppingList.Archived;

            return CreatedAtAction("GetShoppingList", new { id = shoppingListViewModel.Id }, shoppingListViewModel);
        }

        // DELETE: api/ShoppingLists/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteShoppingList(string id)
        {
            var shoppingList = await _service.GetShoppingList(Guid.Parse(id));

            if (shoppingList == null)
            {
                return NotFound();
            }

            await _service.RemoveShoppingList(shoppingList);

            return NoContent();
        }
    }
}
