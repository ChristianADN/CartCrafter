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
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Identity;

namespace ApiRest.Controllers
{
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/[controller]")]
    [ApiController]
    public class CategoriesController : BaseController
    {
        public CategoriesController(UserManager<User> userManager, SignInManager<User> signInManager, ICartCrafterService service, IConfiguration configuration) : base(userManager, signInManager, service, configuration)
        {
        }

        // GET: api/Categories
        //[HttpGet]
        [HttpGet]
        public async Task<ActionResult<IEnumerable<CategoryDto>>> GetCategories()
        {
            return await _service.GetFirstCategories();
        }

        // GET: api/Categories/5
        [HttpGet("{id}")]
        public async Task<ActionResult<CategoryDto>> GetCategory(string id)
        {
            var category = await _service.GetCategory(Guid.Parse(id));

            if (category == null)
            {
                return NotFound();
            }

            return category;
        }

        // GET: api/Categories/ByParent/5
        [HttpGet("byParent/{idParent}")]
        public async Task<ActionResult<IEnumerable<CategoryDto>>> GetCategoriesByParent(string idParent)
        {
            return await _service.GetCategoriesByParent(Guid.Parse(idParent));
        }

        // GET: api/Categories/HasChilds/5
        [HttpGet("hasChilds/{idParent}")]
        public async Task<ActionResult<bool>> HashChilds(string idParent)
        {
            return await _service.CategoryHasChilds(Guid.Parse(idParent));
        }
    }
}
