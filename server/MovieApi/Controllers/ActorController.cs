using Microsoft.AspNetCore.Mvc;
using MovieApi.Data;

namespace MovieApi.Controllers;

[ApiController]
[Route("[controller]")]
public class ActorController : ControllerBase
{
    private readonly IMovieService _movieService;

    public ActorController(IMovieService movieService)
    {
        _movieService = movieService;
    }

    [HttpGet("[action]")]
    public async Task<IEnumerable<ActorDto>> Search([FromQuery] string query)
    {
        return await _movieService.SearchActor(query);
    }
}
