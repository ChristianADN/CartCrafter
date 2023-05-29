using Business.Dto;

namespace ApiRest.ViewModels
{
    public class ProductFullDataViewModel
    {
        public ProductDto Product { get; set; }
        public List<ProductShopViewModel> Prices { get; set; }
        public List<ProductReviewDto> Reviews { get; set; }
        public int AverageRating { get; set; }
        public bool currentUserHasReview { get; set; }
    }
}
