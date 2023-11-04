namespace MovieApi.Data;

public record MovieDto
{
    public required int Id { get; set; }

    public required string Title { get; set; }

    public required int ReleaseYear { get; set; }

    public required string PosterImageUrl { get; set; }
}
