using System.Text.Json.Serialization;

namespace MovieApi.Data;

public record MovieResponse(
    [property: JsonPropertyName("id")] int Id,
    [property: JsonPropertyName("title")] string Title,
    [property: JsonPropertyName("release_date")] string ReleaseDate,
    [property: JsonPropertyName("poster_path")] string PosterPath
);
