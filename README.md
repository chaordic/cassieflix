## Model

+ Movie (application/json)

```json
{
  id: "tt1285016",
  title: "The Social Network",
  year: "2010",
  rated: "PG-13",
  released: "01 Oct 2010",
  runtime: "120 min",
  genre: "Biography, Drama",
  director: "David Fincher",
  writer: "Aaron Sorkin (screenplay), Ben Mezrich (book)",
  actors: "Jesse Eisenberg, Rooney Mara, Bryan Barter, Dustin Fitzsimons",
  plot: "Harvard student Mark Zuckerberg creates the social networking site that would become known as Facebook, but is later sued by two brothers who claimed he stole their idea, and the cofounder who was later squeezed out of the business.",
  language: "English, French",
  country: "USA",
  awards: "Won 3 Oscars. Another 102 wins & 86 nominations.",
  poster: "http://ia.media-imdb.com/images/M/MV5BMTM2ODk0NDAwMF5BMl5BanBnXkFtZTcwNTM1MDc2Mw@@._V1_SX300.jpg",
}
```

+ User (application/json)

## API


* **POST** /movies
 * Inserts a new movie JSON

* **GET** /movies/{movieId}
 * Retrieves a movie JSON by ID

* **POST** /users
 * Inserts a new user JSON

* **GET** /users/{userId}
 * Retrieves a user JSON by id

* **POST** /users/{userId}/rate/{movieId}?stars={rating}
 * Rates a movie

* **GET** /users/{userId}/topRated[?limit={limit}]
 * Get all movies rated by user ordered by rating

* **GET** /users/{userId}
 * Retrieves a user JSON by id

* **POST** /similarMovies/{movieId}
 * Inserts movies similar to the reference movie. The list contains only ids of similar movies ordered by similarity: from most similar to less similar.
   * Request: POST /similarMovies/tt1285016
   * Request body: `["tt0104797", "tt0093389", "tt0986233", "tt0083987", "tt0438859", "tt0422720"]`

* **GET** /similarMovies/{movieId}
 * Returns a list of movies ordered by similarity


