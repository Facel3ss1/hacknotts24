using System.Text.Json.Serialization;

namespace MovieApi.Data;

public record DiscoverMovieResponse(
    [property: JsonPropertyName("page")] int Page,
    [property: JsonPropertyName("results")] List<MovieResponse> Results
);
