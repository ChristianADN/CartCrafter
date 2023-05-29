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
    public partial class CartCarfterService //Reviews
    {
        public async Task<List<ProductReviewDto>> GetProductReviews()
        {
            var reviews =_mapper.Map<List<ProductReviewDto>>(await _context.ProductReviews.ToListAsync());
            foreach(var review in reviews)
            {
                review.UserName = (await _context.Users.FindAsync(review.UserId)).UserName;
            }
            return reviews;
        }
        public async Task<ProductReviewDto> GetProductReview(Guid id)
        {
            var review = _mapper.Map<ProductReviewDto>(await _context.ProductReviews.FindAsync(id.ToString()));
            review.UserName = (await _context.Users.FindAsync(review.UserId)).UserName;
            return review;
        }
        public async Task<List<ProductReviewDto>> GetProductReviewsByProduct(Guid parentId)
        {
            var reviews = _mapper.Map<List<ProductReviewDto>>(await _context.ProductReviews.Where(c => c.ProductId == parentId.ToString()).ToListAsync());
            foreach(var review in reviews)
                review.UserName=GetUserById(Guid.Parse(review.UserId)).Result.UserName;
            return reviews;
        }
        public async Task<ProductReviewDto> UpdateProductReview(ProductReviewDto productReviewDto)
        {
            var productReview = _mapper.Map<ProductReview>(productReviewDto);

            try
            {
                _context.Entry(productReview).State = EntityState.Modified;
                await _context.SaveChangesAsync();
                return await GetProductReview(Guid.Parse(productReviewDto.Id));
            }
            catch (DbUpdateConcurrencyException exception)
            {
                throw exception;
            }
        }
        public async Task<ProductReviewDto> AddProductReview(ProductReviewDto productReviewDto)
        {

            productReviewDto.Id = Guid.NewGuid().ToString();
            var productReview = _mapper.Map<ProductReview>(productReviewDto);

            try
            {
                _context.ProductReviews.Add(productReview);
                await _context.SaveChangesAsync();
                return await GetProductReview(Guid.Parse(productReviewDto.Id));
            }
            catch (DbUpdateException)
            {
                throw;
            }
        }
        public async Task DeleteProductReview(Guid productId, Guid userId)
        {
            var productReview = await _context.ProductReviews.FirstOrDefaultAsync(r => r.ProductId == productId.ToString() && r.UserId == userId.ToString());

            if (productReview != null)
            {
                try
                {
                    _context.ProductReviews.Remove(productReview);
                    await _context.SaveChangesAsync();
                }
                catch (DbUpdateException)
                {
                    throw;
                }
            }
            else
            {
                // Manejar el caso en el que no se encuentre la reseña
                throw new Exception("Product review not found.");
            }
        }
    }
}
