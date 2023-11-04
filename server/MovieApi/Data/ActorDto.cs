namespace MovieApi.Data;

public record ActorDto
{
    public required int Id { get; set; }

    public required string Name { get; set; }

    public required string ProfileImageUrl { get; set; }
}
