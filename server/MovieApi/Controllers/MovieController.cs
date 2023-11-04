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

    [HttpGet("{id:int}")]
    public async Task<ActionResult<MovieDto>> Get(int id)
    {
        var movie = await _movieService.GetMovieById(id);
        if (movie is null)
        {
            return StatusCode(502);
        }

        return movie;
    }

    [HttpGet("[action]")]
    public async Task<ActionResult<IEnumerable<MovieDto>>> Search([FromQuery] string query)
    {
        var movies = await _movieService.SearchMovie(query);
        if (movies is null)
        {
            return StatusCode(502);
        }

        return Ok(movies);
    }

    [HttpGet("[action]")]
    public async Task<ActionResult<StartAndEndMovieDto>> ChooseStartAndEnd()
    {
        var startAndEndMovie = await _movieService.ChooseStartAndEndMovie();
        if (startAndEndMovie is null)
        {
            return StatusCode(502);
        }

        return startAndEndMovie;
    }
}
