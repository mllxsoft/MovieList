package ca.movielist.core;

public interface MovieList extends Iterable<Movie> {
	public void addMovie(Movie movie);
	public void removeMovie(Movie movie);
	public int getMovieQty();
	
	public Movie getMovie(String name);
	public boolean containMovie(String name);
}
