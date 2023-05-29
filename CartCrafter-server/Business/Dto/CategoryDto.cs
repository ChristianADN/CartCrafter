using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Dto
{
    public class CategoryDto
    {
        public string? Id { get; set; } = null!;

        public string Name { get; set; } = null!;

        public string? Description { get; set; }

        public string? ParentId { get; set; }
        public byte[]? Image { get; set; }
        public bool HasChilds { get; set; }
    }
}
