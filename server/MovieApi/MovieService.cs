using MovieApi.Data;
using RestSharp;

namespace MovieApi;

public class MovieService : IMovieService
{
    private const string PosterImageUrlPrefix = "https://image.tmdb.org/t/p/w780";
    private const string ProfileImageUrlPrefix = "https://image.tmdb.org/t/p/w185";
    private const int MaxDiscoverMoviePageNumber = 75;

    private readonly RestClient _client;

    public MovieService(IConfiguration config)
    {
        var tmdbSettings =
            config.GetSection("Tmdb").Get<TmdbSettings>()
            ?? throw new InvalidOperationException("Could not find TMDB settings in config");

        var options = new RestClientOptions("https://api.themoviedb.org/3/");
        _client = new RestClient(options);
        _client.AddDefaultHeader("Accept", "application/json");
        _client.AddDefaultHeader("Authorization", $"Bearer {tmdbSettings.ApiReadAccessKey}");
    }

    public async Task<MovieDto> GetMovieById(int id)
    {
        var request = new RestRequest("movie/{movie_id}");
        request.AddUrlSegment("movie_id", id);

        var response = await _client.GetAsync<MovieResponse>(request);
        if (response is null)
        {
            throw new InvalidOperationException("Get movie request failed");
        }

        return new MovieDto
        {
            Id = response.Id,
            Title = response.Title,
            ReleaseYear = response.ReleaseDate.Year,
            PosterImageUrl = string.IsNullOrEmpty(response.PosterPath)
                ? string.Empty
                : PosterImageUrlPrefix + response.PosterPath,
        };
    }

    public async Task<IEnumerable<MovieDto>> SearchMovie(string query)
    {
        var request = new RestRequest("search/movie");
        request.AddQueryParameter("query", query);
        request.AddQueryParameter("include_adult", true);
        request.AddQueryParameter("language", "en-US");
        request.AddQueryParameter("page", 1);

        var response = await _client.GetAsync<SearchMovieResponse>(request);
        if (response is null)
        {
            throw new InvalidOperationException("Search movie request failed");
        }

        return response.Results.Select(
            movie =>
                new MovieDto
                {
                    Id = movie.Id,
                    Title = movie.Title,
                    ReleaseYear = movie.ReleaseDate.Year,
                    PosterImageUrl = string.IsNullOrEmpty(movie.PosterPath)
                        ? string.Empty
                        : PosterImageUrlPrefix + movie.PosterPath,
                }
        );
    }

    public async Task<IEnumerable<ActorDto>> SearchActor(string query)
    {
        var request = new RestRequest("search/person");
        request.AddQueryParameter("query", query);
        request.AddQueryParameter("include_adult", true);
        request.AddQueryParameter("language", "en-US");
        request.AddQueryParameter("page", 1);

        var response = await _client.GetAsync<SearchPersonResponse>(request);
        if (response is null)
        {
            throw new InvalidOperationException("Search person request failed");
        }

        return response.Results
            .Where(person => person.KnownForDepartment == "Acting")
            .Select(
                person =>
                    new ActorDto
                    {
                        Id = person.Id,
                        Name = person.Name,
                        ProfileImageUrl = string.IsNullOrEmpty(person.ProfilePath)
                            ? string.Empty
                            : ProfileImageUrlPrefix + person.ProfilePath,
                    }
            );
    }

    public async Task<StartAndEndMovieDto> ChooseStartAndEndMovie()
    {
        return new StartAndEndMovieDto
        {
            StartMovieId = await ChooseRandomMovie(),
            EndMovieId = await ChooseRandomMovie(),
        };
    }

    private async Task<int> ChooseRandomMovie()
    {
        var request = new RestRequest("discover/movie");
        request.AddQueryParameter("include_adult", true);
        request.AddQueryParameter("include_video", false);
        request.AddQueryParameter("language", "en-US");
        request.AddQueryParameter("sort_by", "vote_count.desc");

        var page = 1 + Random.Shared.Next(0, MaxDiscoverMoviePageNumber);
        request.AddQueryParameter("page", page);

        var response = await _client.GetAsync<DiscoverMovieResponse>(request);
        if (response is null)
        {
            throw new InvalidOperationException("Discover movie request failed");
        }

        var movies = response.Results;
        var movie = movies[Random.Shared.Next(0, movies.Count)];

        return movie.Id;
    }
}
