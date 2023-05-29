using Business.Dto;
using DataAccess.Models;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Service
{
    public partial interface ICartCrafterService
    {
        Task<List<ProductDto>> GetProducts();
        Task<ProductDto> GetProduct(Guid id);
        Task<List<ProductDto>> GetProductsByType(Guid id);
        //Task<List<ProductDto>> GetProductsByShop(Guid id);
        Task<ProductDto> UpdateProduct(ProductDto productDto);
        Task<ProductDto> AddProduct(ProductDto productDto);
    }
}
