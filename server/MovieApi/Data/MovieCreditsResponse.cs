using System.Text.Json.Serialization;

namespace MovieApi.Data;

public record MovieCreditsResponse(
    [property: JsonPropertyName("id")] int Id,
    [property: JsonPropertyName("cast")] List<PersonResponse> Cast
);
