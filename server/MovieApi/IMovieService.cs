using MovieApi.Data;

namespace MovieApi;

public interface IMovieService
{
    Task<MovieDto> GetMovieById(int id);

    Task<IEnumerable<ActorDto>> SearchActor(string query);

    Task<StartAndEndMovieDto> ChooseStartAndEndMovie();
}
