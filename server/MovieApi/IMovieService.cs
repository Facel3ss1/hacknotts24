using MovieApi.Data;

namespace MovieApi;

public interface IMovieService
{
    Task<string> TestEndpoint();

    Task<MovieDto> GetMovieById(int id);

    Task<StartAndEndMovieDto> ChooseStartAndEndMovie();
}
