package ca.movielist.MvcImplementation;

import javax.swing.JOptionPane;

import ca.movielist.core.Movie;
import ca.movielist.core.imdb.MovieImdb;
import ca.movielist.core.imdb.MovieLookupImdb;

public class MovieListController {
	
	private MovieListView view = null;
	private MovieModel model = null;
	private MovieLookupImdb lookup = null;
	
	private boolean dirtyChange = false;
	
	public MovieListController(MovieModel model) {
		// Keep a reference on the model
		this.model = model;
		
		// Create the view
		this.view = new MovieListView(this);

		this.lookup = new MovieLookupImdb();
		
		// Bind the model with the view
		model.addListDataListener(this.view);
	}
	
	public void logMessage(String message) {
		System.out.println(message);
		this.view.appendLineToStatus(message);
	}
	
	public void removeMovie(String name) {
		logMessage("Remove movie: " + name);
		
		Movie movieToRemove = model.getMovie(name);
		if(movieToRemove != null) {
			model.removeMovie(movieToRemove);
			logMessage("      -> Movie removed!");
			dirtyChange = true;
		}
	}

	private class MovieLookupJob implements Runnable {

		private Movie movie;
		
		public MovieLookupJob(Movie movieToLookup) {
			this.movie = movieToLookup;
		}
		
		@Override
		public void run() {
			lookup.lookupInformations(movie);
			// TODO Auto-generated method stub
			if(!model.containMovie(movie.getName())) {
				model.addMovie(movie);
				logMessage("      -> Movie added!");
			} else {
				model.fireMovieListChanged();
			}
		}
	}
	
	// Search button is pressed
	public void searchMovie(String name) {
		logMessage("Search movie: " + name);
		
		// add / search a new movie TODO
		MovieImdb newMovie = new MovieImdb(name);
		
		// Find 19 or 20 followed with two numbers
		int pos = name.indexOf("19");
		if( pos > -1 ) {
			String s1 = name.substring(pos, pos+4);
			
			try {
				Integer year = Integer.valueOf(s1);
				newMovie.setName(name.substring(0, pos-1) + name.substring(pos+4));
				newMovie.setYear(year);
			} catch (Exception e) {
				logMessage("      -> No 19xx date found");
			}
		}

		pos = name.indexOf("20");
		if( pos > -1 ) {
			String s1 = name.substring(pos, pos+4);
			
			try {
				Integer year = Integer.valueOf(s1);
				newMovie.setName(name.substring(0, pos-1) + name.substring(pos+4));
				newMovie.setYear(year);
			} catch (Exception e) {
				logMessage("      -> No 20xx date found");
			}
		}
		
		// TODO Launch this process in a separate thread
		new Thread(new MovieLookupJob(newMovie)).start();
		
		dirtyChange = true;
	}
	
	public void browseMovie(String name) {
		try {
			MovieImdb movieImdb = (MovieImdb)model.getMovie(name);
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
			logMessage("Browsing url: " +movieImdb.getUrl().toURI());
		    desktop.browse( movieImdb.getUrl().toURI() );
		}
		catch ( Exception e ) {
			System.err.println( e.getMessage() );
		}
	}
	
	private TorrentZUrlBuilder urlBuilder = new TorrentZUrlBuilder(); 
	public void torrentMovie(String name) {
		
		try {
			MovieImdb movieImdb = (MovieImdb)model.getMovie(name);
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
			logMessage("Browsing url: " + urlBuilder.buildUrl(movieImdb).toURI());
			desktop.browse( urlBuilder.buildUrl(movieImdb).toURI() );
		}
		catch ( Exception e ) {
			System.err.println( e.getMessage() );
		}
	}
	
	public void refreshMovie(String name) {
		
		try {
			MovieImdb movieImdb = (MovieImdb)model.getMovie(name);
			logMessage("Refresh movie information: " + name);
			lookup.lookupInformations(movieImdb);
		}
		catch ( Exception e ) {
			System.err.println( e.getMessage() );
		}
	}
	
	private String currentFilename = "application_db.data";
	
	public void displayView() {
		String newFilename = this.view.chooseFile();
		if(!newFilename.isEmpty()) {
			currentFilename = newFilename;
		}
		logMessage("Loading database: " + currentFilename);
		this.model.LoadMovies(currentFilename);
		view.display();		
	}
	
	public void saveAs() {
		String newFilename = this.view.chooseFile();
		if(!newFilename.isEmpty() && !newFilename.equals(currentFilename)) {
			currentFilename = newFilename;
			logMessage("Saving database: " + currentFilename);
			this.model.SaveMovies(currentFilename);
			this.dirtyChange = false;
		}
	}
	
	public void openOther() {
		if(dirtyChange) {	
			int answer = this.view.askConfirmation(currentFilename);
			if(answer == JOptionPane.YES_OPTION) {
				logMessage("Saving database: " + currentFilename);
				this.model.SaveMovies(currentFilename);
			} else if(answer == JOptionPane.CANCEL_OPTION) {
				return;
			} else {
				// NO_OPTION, DO NOTHING
			}
		}
		
		String newFilename = this.view.chooseFile();
		if(!newFilename.isEmpty() && !newFilename.equals(currentFilename)) {
			currentFilename = newFilename;
			logMessage("Loading new database: " + currentFilename);
			this.model.LoadMovies(currentFilename);
		}
		this.dirtyChange = false;
	}
	
	public void exitApp() {
		if(dirtyChange) {	
			int answer = this.view.askConfirmation(currentFilename);
			if(answer == JOptionPane.YES_OPTION) {
				logMessage("Saving database to: " + currentFilename);
				this.model.SaveMovies(currentFilename);
				System.exit(0);
			} else if(answer == JOptionPane.NO_OPTION) {
				System.exit(0);
			}
		} else {
			// TODO else ask for exit confirmation without saving
			System.exit(0);
		}
	}
}
