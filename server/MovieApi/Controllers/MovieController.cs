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
        return await _movieService.GetMovieById(id);
    }

    [HttpGet("[action]")]
    public async Task<IEnumerable<MovieDto>> Search([FromQuery] string query)
    {
        return await _movieService.SearchMovie(query);
    }

    [HttpGet("[action]")]
    public async Task<ActionResult<StartAndEndMovieDto>> ChooseStartAndEnd()
    {
        return await _movieService.ChooseStartAndEndMovie();
    }
}
