using System.Globalization;
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

    public async Task<MovieDto?> GetMovieById(int id)
    {
        var request = new RestRequest("movie/{movie_id}");
        request.AddUrlSegment("movie_id", id);

        var response = await _client.GetAsync<MovieResponse>(request);
        if (response is null)
        {
            return null;
        }

        int? releaseYear = null;
        if (
            DateOnly.TryParse(
                response.ReleaseDate,
                CultureInfo.InvariantCulture,
                DateTimeStyles.None,
                out var releaseDate
            )
        )
        {
            releaseYear = releaseDate.Year;
        }

        return new MovieDto
        {
            Id = response.Id,
            Title = response.Title,
            ReleaseYear = releaseYear,
            PosterImageUrl = string.IsNullOrEmpty(response.PosterPath)
                ? null
                : PosterImageUrlPrefix + response.PosterPath,
        };
    }

    public async Task<IEnumerable<MovieDto>?> SearchMovie(string query)
    {
        var request = new RestRequest("search/movie");
        request.AddQueryParameter("query", query);
        request.AddQueryParameter("include_adult", true);
        request.AddQueryParameter("language", "en-US");
        request.AddQueryParameter("page", 1);

        var response = await _client.GetAsync<SearchMovieResponse>(request);

        return response?.Results.Select(movie =>
        {
            int? releaseYear = null;
            if (
                DateOnly.TryParse(
                    movie.ReleaseDate,
                    CultureInfo.InvariantCulture,
                    DateTimeStyles.None,
                    out var releaseDate
                )
            )
            {
                releaseYear = releaseDate.Year;
            }

            return new MovieDto
            {
                Id = movie.Id,
                Title = movie.Title,
                ReleaseYear = releaseYear,
                PosterImageUrl = string.IsNullOrEmpty(movie.PosterPath)
                    ? null
                    : PosterImageUrlPrefix + movie.PosterPath,
            };
        });
    }

    public async Task<IEnumerable<ActorDto>?> SearchActor(string query)
    {
        var request = new RestRequest("search/person");
        request.AddQueryParameter("query", query);
        request.AddQueryParameter("include_adult", true);
        request.AddQueryParameter("language", "en-US");
        request.AddQueryParameter("page", 1);

        var response = await _client.GetAsync<SearchPersonResponse>(request);

        return response?.Results
            .Where(person => person.KnownForDepartment == "Acting")
            .Select(
                person =>
                    new ActorDto
                    {
                        Id = person.Id,
                        Name = person.Name,
                        ProfileImageUrl = string.IsNullOrEmpty(person.ProfilePath)
                            ? null
                            : ProfileImageUrlPrefix + person.ProfilePath,
                    }
            );
    }

    public async Task<bool?> MovieHasActor(int movieId, int actorId)
    {
        var request = new RestRequest("movie/{movie_id}/credits");
        request.AddUrlSegment("movie_id", movieId);

        var response = await _client.GetAsync<MovieCreditsResponse>(request);

        return response?.Cast.Any(person => person.Id == actorId);
    }

    public async Task<StartAndEndMovieDto?> ChooseStartAndEndMovie()
    {
        var startMovieId = await ChooseRandomMovie();
        var endMovieId = await ChooseRandomMovie();

        if (startMovieId is null || endMovieId is null)
        {
            return null;
        }

        return new StartAndEndMovieDto
        {
            StartMovieId = startMovieId.Value,
            EndMovieId = endMovieId.Value,
        };
    }

    private async Task<int?> ChooseRandomMovie()
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
            return null;
        }

        var movies = response.Results;
        var movie = movies[Random.Shared.Next(0, movies.Count)];

        return movie.Id;
    }
}
