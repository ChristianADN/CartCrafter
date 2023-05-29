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

namespace ApiRest.Controllers
{
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/[controller]")]
    [ApiController]
    public class ProductTypesController : BaseController
    {
        public ProductTypesController(UserManager<User> userManager, SignInManager<User> signInManager,ICartCrafterService service, IConfiguration configuration) : base(userManager, signInManager, service, configuration)
        {
        }

        // GET: api/ProductTypes
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ProductTypeDto>>> GetProductTypes()
        {
            return await _service.GetProductTypes();
        }

        // GET: api/ProductTypes/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ProductTypeDto>> GetProductType(string id)
        {
            var ProductType = await _service.GetProductType(Guid.Parse(id));

            if (ProductType == null)
            {
                return NotFound();
            }

            return ProductType;
        }

        // GET: api/ProductTypes/ByCategory/5
        [HttpGet("ByCategory/{id}")]
        public async Task<ActionResult<IEnumerable<ProductTypeDto>>> GetProductTypeByCategory(string id)
        {
            var ProductTypes = await _service.GetProductTypesByCategory(Guid.Parse(id));

            if (ProductTypes.Count==0)
            {
                return NotFound();
            }

            return ProductTypes;
        }

        // PUT: api/ProductTypes/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutProductType(string id, ProductTypeDto productTypeDto)
        {
            if (id != productTypeDto.Id)
            {
                return BadRequest();
            }

            try
            {
                await _service.UpdateProductType(productTypeDto);
            }
            catch (DbUpdateConcurrencyException)
            {
                return NotFound();
            }

            return NoContent();
        }

        // POST: api/ProductTypes
        [HttpPost]
        public async Task<ActionResult<ProductTypeDto>> PostProductType(ProductTypeDto productTypeDto)
        {
            try
            {
                await _service.AddProductType(productTypeDto);
            }
            catch (DbUpdateException)
            {
                return Conflict();
            }

            return CreatedAtAction("GetProductType", new { id = productTypeDto.Id }, productTypeDto);
        }
    }
}
