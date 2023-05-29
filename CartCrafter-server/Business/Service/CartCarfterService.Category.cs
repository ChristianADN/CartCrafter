using Business.Dto;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Runtime.InteropServices;
using System.Text;
using System.Threading.Tasks;

namespace Business.Service
{
    public partial class CartCarfterService //Categories
    {
        public async Task<List<CategoryDto>> GetFirstCategories()
        {
            return _mapper.Map<List<CategoryDto>>(await _context.Categories.Where(c=>c.ParentId==null).ToListAsync());
        }
        public async Task<List<CategoryDto>> GetCategories()
        {
            return _mapper.Map<List<CategoryDto>>(await _context.Categories.ToListAsync());
        }
        public async Task<CategoryDto> GetCategory(Guid id)
        {
            return _mapper.Map<CategoryDto>(await _context.Categories.FindAsync(id.ToString()));
        }
        public async Task<List<CategoryDto>> GetCategoriesByParent(Guid parentId)
        {
            return _mapper.Map<List<CategoryDto>>(await _context.Categories.Where(c => c.ParentId == parentId.ToString()).ToListAsync());
        }
        public async Task<bool> CategoryHasChilds(Guid parentId)
        {
            var response =_context.Categories.Where(c => c.ParentId == parentId.ToString()).Count()>0;
            return response;
        }
    }
}
