using AutoMapper;
using Business.Dto;
using DataAccess.Data;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Service
{
    public partial class CartCarfterService : ICartCrafterService
    {
        private readonly CartCrafterDbContext _context;
        private readonly IMapper _mapper;

        public CartCarfterService(CartCrafterDbContext context, IMapper mapper)
        {
            _mapper = mapper;
            _context = context;
        }
    }
}
