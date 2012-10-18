package ca.movielist.MvcImplementation;


import javax.swing.event.EventListenerList;
import ca.movielist.core.Movie;
import ca.movielist.core.imdb.*;

// TODO devrais hériter de listmodel ou simplement contenir des listeners ?
public class MovieModel extends MovieListImdb {
	
	public MovieModel() {
		System.out.println("MovieModel()");
		
		listeners = new EventListenerList();
		// movieList.load()...
	}

	private EventListenerList listeners;
	
	public void addListDataListener(MovieListListener view) {
		listeners.add(MovieListListener.class, view);
	}

	public void removeListDataListener(MovieListListener listener) {
		listeners.remove(MovieListListener.class, listener);
	}

	public void fireMovieListChanged(){
		MovieListListener[] listenerList = (MovieListListener[])listeners.getListeners(MovieListListener.class);
		
		for(MovieListListener listener : listenerList){
			listener.movieListChanged(new MovieListChangedEvent(this)); 
			// TODO implement other kind of changement or create my own custom ListDataListener and event...
		}
	}
	
	@Override
	public void addMovie(Movie movie) {
		super.addMovie(movie);
		fireMovieListChanged();
		System.out.println("MODEL: addMovie...");
	}
	
	@Override
	public void removeMovie(Movie movie) {
		super.removeMovie(movie);
		fireMovieListChanged();
		System.out.println("MODEL: removeMovie...");
	}
	
	
	@Override
	public void LoadMovies(String filepath) {
		super.LoadMovies(filepath);
		fireMovieListChanged();
		System.out.println("MODEL: LoadMovies...");
	}
}
