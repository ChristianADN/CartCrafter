namespace ApiRest.ViewModels
{
    public class ReviewViewModel
    {
        public string ProductId { get; set; } = null!;

        public int Rating { get; set; }

        public byte[]? Image { get; set; }

        public string? Text { get; set; }
    }
}
