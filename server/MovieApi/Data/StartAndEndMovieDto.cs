namespace MovieApi.Data;

public record StartAndEndMovieDto
{
    public required int StartMovieId { get; set; }

    public required int EndMovieId { get; set; }
}
