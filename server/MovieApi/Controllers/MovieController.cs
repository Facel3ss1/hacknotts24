using Microsoft.AspNetCore.Mvc;
using MovieApi.Data;

namespace MovieApi.Controllers;

[ApiController]
[Route("[controller]")]
public class MovieController : ControllerBase
{
    private readonly IMovieService _movieService;

    public MovieController(IMovieService movieService)
    {
        _movieService = movieService;
    }

    [HttpGet("Test")]
    public async Task<IActionResult> GetAsync()
    {
        return Content(await _movieService.TestEndpoint(), "application/json");
    }

    [HttpGet("ChooseStartAndEnd")]
    public async Task<ActionResult<StartAndEndMovieDto>> ChooseStartAndEndMovie()
    {
        return await _movieService.ChooseStartAndEndMovie();
    }
}
