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
        Task<List<ProductTypeDto>> GetProductTypes();
        Task<ProductTypeDto> GetProductType(Guid id);
        Task<List<ProductTypeDto>> GetProductTypesByCategory(Guid id);
        Task<ProductTypeDto> UpdateProductType(ProductTypeDto productTypeDto);
        Task<ProductTypeDto> AddProductType(ProductTypeDto productTypeDto);
    }
}
