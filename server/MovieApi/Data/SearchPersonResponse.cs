using System.Text.Json.Serialization;

namespace MovieApi.Data;

public record SearchPersonResponse(
    [property: JsonPropertyName("page")] int Page,
    [property: JsonPropertyName("results")] List<PersonResponse> Results
);