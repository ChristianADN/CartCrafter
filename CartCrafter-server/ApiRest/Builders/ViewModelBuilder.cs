using ApiRest.ViewModels;
using Business.Dto;
using Business.Service;
using DataAccess.Models;

namespace ApiRest.Builders
{
    public static class ViewModelBuilder
    {
        public static ProductFullDataViewModel GetProductWithFullData(Guid productId, ICartCrafterService service)
        {
            ProductDto product = service.GetProduct(productId).Result;
            if (product == null)
                return null;

            List<ProductReviewDto> reviews = service.GetProductReviewsByProduct(productId).Result;
            List<ProductShopViewModel> prices = GetProductShopsViewModel(productId, service);
            int averageRating = reviews.Any() ? (int)reviews.Average(r => r.Rating) : 0;

            ProductFullDataViewModel productFullDataViewModel = new ProductFullDataViewModel
            {
                Product = product,
                Reviews = reviews,
                Prices = prices,
                AverageRating = averageRating
            };

            return productFullDataViewModel;
        }
        public static List<ProductShopViewModel> GetProductShopsViewModel(Guid productId, ICartCrafterService service)
        {
            List<ProductShopDto> productShops = service.GetProductShopsByProduct(productId).Result;

            List<ProductShopViewModel> productShopsViewModel = new();

            foreach (ProductShopDto productShop in productShops)
            {
                var shop = service.GetShop(Guid.Parse(productShop.ShopId)).Result;

                ProductShopViewModel productShopViewModel = new ProductShopViewModel
                {
                    DateAdded = productShop.DateAdded,
                    Id = productShop.Id,
                    Price = productShop.Price,
                    Shop = shop
                };
                productShopsViewModel.Add(productShopViewModel);
            }

            return productShopsViewModel;

        }
        public static async Task<ShoppingListViewModel> ConvertToShoppingListViewModel(ShoppingListDto shoppingListDto, ICartCrafterService service)
        {
            List<ShoppingListProductDto> shoppingListProducts = await service.GetShoppingListProductsByShoppingList(Guid.Parse(shoppingListDto.Id));

            float totalWeight = shoppingListProducts.Sum(p => service.GetProduct(Guid.Parse(p.ProductId)).Result.Weight);

            ShoppingListViewModel shoppingListViewModel = new ShoppingListViewModel
            {
                Id = shoppingListDto.Id,
                UserId = shoppingListDto.UserId,
                DateCreated = shoppingListDto.DateCreated,
                Archived = shoppingListDto.Archived,
                TotalWeight = totalWeight,
                ProductCount = shoppingListProducts.Count
            };

            return shoppingListViewModel;
        }

    }
}
