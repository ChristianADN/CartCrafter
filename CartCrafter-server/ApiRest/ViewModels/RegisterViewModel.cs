using System.ComponentModel.DataAnnotations;

namespace ApiRest.ViewModels
{
    public class RegisterViewModel
    {
        public string UserName { get; set; }
        [Required]
        public string Email { get; set; }
        [Required]
        public string Password { get; set; }
    }
}
