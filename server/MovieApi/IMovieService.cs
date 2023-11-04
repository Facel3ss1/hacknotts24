using MovieApi.Data;

namespace MovieApi;

public interface IMovieService
{
    Task<MovieDto?> GetMovieById(int id);

    Task<IEnumerable<MovieDto>?> SearchMovie(string query);

    Task<IEnumerable<ActorDto>?> SearchActor(string query);

    Task<bool?> MovieHasActor(int movieId, int actorId);

    Task<StartAndEndMovieDto?> ChooseStartAndEndMovie();
}
