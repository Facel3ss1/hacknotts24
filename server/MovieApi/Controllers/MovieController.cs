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
    public async Task<IActionResult> TestEndpoint()
    {
        return Content(await _movieService.TestEndpoint(), "application/json");
    }

    [HttpGet("{id:int}")]
    public async Task<ActionResult<MovieDto>> Get(int id)
    {
        return await _movieService.GetMovieById(id);
    }

    [HttpGet("ChooseStartAndEnd")]
    public async Task<ActionResult<StartAndEndMovieDto>> ChooseStartAndEndMovie()
    {
        return await _movieService.ChooseStartAndEndMovie();
    }
}
