using Business.Dto;

namespace ApiRest.ViewModels
{
    public class ProductShopViewModel
    {
        public string Id { get; set; }
        public decimal Price { get; set; }
        public DateTime DateAdded { get; set; }
        public ShopDto Shop { get; set; }
    }
}
