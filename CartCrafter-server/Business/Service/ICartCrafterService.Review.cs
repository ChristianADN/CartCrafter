using Business.Dto;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Service
{
    public partial interface ICartCrafterService
    {
        Task<List<ProductReviewDto>> GetProductReviews();
        Task<ProductReviewDto> GetProductReview(Guid id);
        Task<List<ProductReviewDto>> GetProductReviewsByProduct(Guid id);
        Task<ProductReviewDto> UpdateProductReview(ProductReviewDto productReviewDto);
        Task<ProductReviewDto> AddProductReview(ProductReviewDto productReviewDto);
        Task DeleteProductReview(Guid productId, Guid userId);

    }
}
