﻿using MovieApi.Data;
using RestSharp;

namespace MovieApi;

public class MovieService : IMovieService
{
    private const string PosterImageUrlPrefix = "https://image.tmdb.org/t/p/w780";
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

    public async Task<string> TestEndpoint()
    {
        var request = new RestRequest("authentication");
        var response = await _client.GetAsync(request);

        return response.Content ?? "{}";
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
            PosterImageUrl = PosterImageUrlPrefix + response.PosterPath,
        };
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
        request.AddQueryParameter("include_adult", "true");
        request.AddQueryParameter("include_video", "false");
        request.AddQueryParameter("language", "en-US");
        request.AddQueryParameter("sort_by", "vote_count.desc");

        var page = 1 + Random.Shared.Next(0, MaxDiscoverMoviePageNumber);
        request.AddQueryParameter("page", page.ToString());

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
