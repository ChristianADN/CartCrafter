using Business.Service;
using DataAccess.Models;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;

namespace ApiRest.Controllers
{
    public class BaseController : ControllerBase
    {
        protected readonly ICartCrafterService _service;
        protected readonly IConfiguration _configuration;
        protected readonly UserManager<User> _userManager;
        protected readonly SignInManager<User> _signInManager;
        public BaseController(UserManager<User> userManager, SignInManager<User> signInManager, ICartCrafterService service, IConfiguration configuration) : base()
        {
            _service = service;
            _configuration = configuration;
            _userManager = userManager;
            _signInManager = signInManager;
        }
    }
}
