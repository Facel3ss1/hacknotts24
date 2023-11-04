using System.Text.Json.Serialization;

namespace MovieApi.Data;

public record SearchMovieResponse(
    [property: JsonPropertyName("page")] int Page,
    [property: JsonPropertyName("results")] List<MovieResponse> Results
);
