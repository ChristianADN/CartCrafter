namespace ApiRest.ViewModels
{
    public class ShoppingListViewModel
    {
        public string Id { get; set; } = null!;
        public string UserId { get; set; } = null!;
        public DateTime DateCreated { get; set; }
        public bool Archived { get; set; }
        public float TotalWeight { get; set; }
        public int ProductCount { get; set; }
    }

}
