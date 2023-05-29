using Business.Dto;
using DataAccess.Models;
using Microsoft.EntityFrameworkCore;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Business.Service
{
    public partial class CartCarfterService // Users
    {
        public async Task<UserDto> GetUserById(Guid userId)
        {
            return _mapper.Map<UserDto>(await _context.Users.FindAsync(userId.ToString()));
        }
    }
}
