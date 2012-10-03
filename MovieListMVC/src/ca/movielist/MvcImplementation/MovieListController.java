package ca.movielist.MvcImplementation;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.net.URI;
import java.util.Iterator;

import ca.movielist.core.Movie;
import ca.movielist.core.imdb.MovieImdb;
import ca.movielist.core.imdb.MovieLookupImdb;

public class MovieListController {
	
	private MovieListView view = null;
	private MovieModel model = null;
	private MovieLookupImdb lookup = null;
	
	public MovieListController(MovieModel model) {
		// Keep a reference on the model
		this.model = model;
		
		// Create the view
		this.view = new MovieListView(this);

		this.lookup = new MovieLookupImdb();
		
		// Bind the model with the view
		model.addListDataListener(this.view);
	}
	
	public void removeMovie(String name) {
		System.out.println("Controller remove movie..." + name);
		
		Movie movieToRemove = model.getMovie(name);
		if(movieToRemove != null) {
			model.removeMovie(movieToRemove);
		}
	}

	// Search button is pressed
	public void searchMovie(String name) {
		System.out.println("Controller search movie..." + name);
		
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
				System.out.println("No 19xx date found");
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
				System.out.println("No 20xx date found");
			}
		}
		
		// TODO Launch this process in a separate thread
		lookup.lookupInformations(newMovie);
		
		this.model.addMovie(newMovie);
	}
	
	public void browseMovie(String name) {
		try {
			MovieImdb movieImdb = (MovieImdb)model.getMovie(name);
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();
		    desktop.browse( movieImdb.getUrl().toURI() );
		}
		catch ( Exception e ) {
			System.err.println( e.getMessage() );
		}
	}
	
	public void exportToCsv() {
		Iterator<Movie> iterator = model.iterator();
		
		try {
			FileWriter outFile = new FileWriter("Backup.csv");
			PrintWriter out = new PrintWriter(outFile);

			out.println("name, year, rating, id, url");
			
			while(iterator.hasNext()) {
				MovieImdb movie = (MovieImdb) iterator.next();	

				out.print(movie.getName());
				out.print(",");
				out.print(movie.getYear());
				out.print(",");
				out.print(movie.getRating());
				out.print(",");
				out.print(movie.getId());
				out.print(",");
				out.println(movie.getUrl());
			}			
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void displayView() {
		this.model.LoadMovies("application_db.data");
		view.display();		
	}
	
	public void exitApp() {
		this.model.SaveMovies("application_db.data");
		exportToCsv();
		System.exit(0);
	}
}
