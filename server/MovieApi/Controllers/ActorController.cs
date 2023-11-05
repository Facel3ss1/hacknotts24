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
    public async Task<ActionResult<IEnumerable<ActorDto>>> Search([FromQuery] string query)
    {
        var actors = await _movieService.SearchActor(query);
        if (actors is null)
        {
            return StatusCode(StatusCodes.Status502BadGateway);
        }

        return Ok(actors);
    }
}
