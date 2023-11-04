using MovieApi.Data;

namespace MovieApi;

public interface IMovieService
{
    Task<string> TestEndpoint();

    Task<StartAndEndMovieDto> ChooseStartAndEndMovie();
}
