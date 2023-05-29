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
        Task<List<ShopDto>> GetShops();
        Task<ShopDto> GetShop(Guid id);
    }
}
