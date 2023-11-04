using Microsoft.AspNetCore.Mvc;
using RestSharp;

namespace MovieApi.Controllers;

[ApiController]
[Route("[controller]")]
public class MovieController : ControllerBase
{
    private readonly TmdbSettings _tmdbSettings;

    public MovieController(IConfiguration config)
    {
        _tmdbSettings =
            config.GetSection("Tmdb").Get<TmdbSettings>()
            ?? throw new InvalidOperationException("Could not find TMDB settings in config");
    }

    [HttpGet]
    public async Task<IActionResult> GetAsync()
    {
        var options = new RestClientOptions("https://api.themoviedb.org/3/authentication");
        var client = new RestClient(options);
        var request = new RestRequest("");
        request.AddHeader("accept", "application/json");
        request.AddHeader("Authorization", $"Bearer {_tmdbSettings.ApiReadAccessKey}");
        var response = await client.GetAsync(request);

        return Content(response.Content ?? "{}", "application/json");
    }
}
