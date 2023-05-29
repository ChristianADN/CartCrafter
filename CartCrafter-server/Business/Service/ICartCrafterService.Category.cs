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
        Task<List<CategoryDto>> GetCategories();
        Task<List<CategoryDto>> GetFirstCategories();
        Task<CategoryDto> GetCategory(Guid id);
        Task<List<CategoryDto>> GetCategoriesByParent(Guid id);
        Task<bool> CategoryHasChilds(Guid id);
    }
}
