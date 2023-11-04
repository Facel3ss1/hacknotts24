using System.Text.Json.Serialization;

namespace MovieApi.Data;

public record PersonResponse(
    [property: JsonPropertyName("id")] int Id,
    [property: JsonPropertyName("name")] string Name,
    [property: JsonPropertyName("profile_path")] string ProfilePath,
    [property: JsonPropertyName("known_for_department")] string KnownForDepartment
);
