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
    public class ProductsController : BaseController
    {
        public ProductsController(UserManager<User> userManager, SignInManager<User> signInManager,ICartCrafterService service, IConfiguration configuration) : base(userManager, signInManager, service, configuration)
        {
        }

        // GET: api/Products
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ProductDto>>> GetProducts()
        {
            return await _service.GetProducts();
        }

        // GET: api/Products/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ProductDto>> GetProduct(string id)
        {
            var product = await _service.GetProduct(Guid.Parse(id));

            if (product == null)
            {
                return NotFound();
            }

            return product;
        }

        // GET: api/Products/fullData/5
        [HttpGet("fullData/{id}")]
        public async Task<ActionResult<ProductFullDataViewModel>> GetFullDataProduct(string id)
        {
            var user = await _userManager.FindByNameAsync(User.Identity.Name);
            var product = ViewModelBuilder.GetProductWithFullData(Guid.Parse(id),_service);

            if (product == null)
            {
                return NotFound();
            }
            product.currentUserHasReview = product.Reviews.FirstOrDefault(r => r.UserId == user.Id) != null;
            return product;
        }

        // GET: api/Products/ByProductType/5
        [HttpGet("ByProductType/{id}")]
        public async Task<ActionResult<IEnumerable<ProductDto>>> GetProductByProductType(string id)
        {
            var products = await _service.GetProductsByType(Guid.Parse(id));

            if (products.Count==0)
            {
                return NotFound();
            }

            return products;
        }
        /*
        // GET: api/Products/ByShop/5
        [HttpGet("ByShop/{id}")]
        public async Task<ActionResult<IEnumerable<ProductDto>>> GetProductByShop(string id)
        {
            var products = await _service.GetProductsByShop(Guid.Parse(id));

            if (products.Count == 0)
            {
                return NotFound();
            }

            return products;
        }
        */
        // PUT: api/Products/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutProduct(string id, ProductDto product)
        {
            if (id != product.Id)
            {
                return BadRequest();
            }

            try
            {
                await _service.UpdateProduct(product);
            }
            catch (DbUpdateConcurrencyException)
            {
                return NotFound();
            }

            return NoContent();
        }

        // POST: api/Products
        [HttpPost]
        public async Task<ActionResult<ProductDto>> PostProduct(ProductDto product)
        {
            try
            {
                await _service.AddProduct(product);
            }
            catch (DbUpdateException)
            {
                return Conflict();
            }

            return CreatedAtAction("GetProduct", new { id = product.Id }, product);
        }

        /*
        // DELETE: api/Products/5
        [HttpDelete("{id}")]
        public async Task<IActionResult> DeleteProduct(string id)
        {
            var product = await _context.Products.FindAsync(id);
            if (product == null)
            {
                return NotFound();
            }

            _context.Products.Remove(product);
            await _context.SaveChangesAsync();

            return NoContent();
        }

        private bool ProductExists(string id)
        {
            return _context.Products.Any(e => e.Id == id);
        }
        */
    }
}
