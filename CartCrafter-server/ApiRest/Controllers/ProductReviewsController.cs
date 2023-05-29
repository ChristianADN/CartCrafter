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
using ApiRest.ViewModels;
using Microsoft.AspNetCore.Identity;

namespace ApiRest.Controllers
{
    [Authorize(AuthenticationSchemes = JwtBearerDefaults.AuthenticationScheme)]
    [Route("api/[controller]")]
    [ApiController]
    public class ProductReviewsController : BaseController
    {
        public ProductReviewsController(UserManager<User> userManager, SignInManager<User> signInManager,ICartCrafterService service, IConfiguration configuration) : base(userManager, signInManager, service, configuration)
        {
        }

        // GET: api/ProductReviews
        [HttpGet]
        public async Task<ActionResult<IEnumerable<ProductReviewDto>>> GetProductReviews()
        {
            return await _service.GetProductReviews();
        }

        // GET: api/ProductReviews/5
        [HttpGet("{id}")]
        public async Task<ActionResult<ProductReviewDto>> GetProductReview(string id)
        {
            var productReview = await _service.GetProductReview(Guid.Parse(id));

            if (productReview == null)
            {
                return NotFound();
            }

            return productReview;
        }

        // GET: api/ProductReviews/ByProduct/5
        [HttpGet("byProduct{id}")]
        public async Task<ActionResult<IEnumerable<ProductReviewDto>>> GetProductReviewByProduct(string id)
        {
            var productReview = await _service.GetProductReviewsByProduct(Guid.Parse(id));

            if (productReview.Count==0)
            {
                return NotFound();
            }

            return productReview;
        }

        // PUT: api/ProductReviews/5
        [HttpPut("{id}")]
        public async Task<IActionResult> PutProductReview(string id, ProductReviewDto productReview)
        {
            if (id != productReview.Id)
            {
                return BadRequest();
            }

            try
            {
                await _service.UpdateProductReview(productReview);
            }
            catch (DbUpdateConcurrencyException)
            {
                return NotFound();
            }

            return NoContent();
        }

        // POST: api/ProductReviews
        [HttpPost]
        public async Task<ActionResult<ProductReviewDto>> PostProductReview(ReviewViewModel model)
        {
            try
            {
                var user = await _userManager.FindByNameAsync(User.Identity.Name);
                var productReviewDto = new ProductReviewDto
                {
                    ProductId = model.ProductId,
                    Rating = model.Rating,
                    Text = model.Text,
                    Image = model.Image,
                    UserId = user.Id
                };
                productReviewDto = await _service.AddProductReview(productReviewDto);

                return CreatedAtAction("GetProductReview", new { id = productReviewDto.Id }, productReviewDto);
            }
            catch (DbUpdateException)
            {
                return Conflict();
            }

        }

        // GET: api/deletebyproductid
        [HttpGet("deletebyproductid/{productId}")]
        public async Task<ActionResult> PostProductReview(string productId)
        {
            try
            {
                var user = await _userManager.FindByNameAsync(User.Identity.Name);
                await _service.DeleteProductReview(Guid.Parse(productId),Guid.Parse(user.Id));

                return Ok();
            }
            catch (Exception)
            {
                return BadRequest();
            }

        }
    }
}
