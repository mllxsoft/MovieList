package ca.movielist.core.imdb;

import java.io.FileReader;
import java.io.FileWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import ca.movielist.core.Movie;
import ca.movielist.core.MovieList;

public class MovieListImdb implements MovieList {

	// Rough implementation
	ArrayList<Movie> movies = new ArrayList<Movie>();
	
	@Override
	public void addMovie(Movie movie) {
		// TODO Auto-generated method stub
		movies.add(movie);
	}

	@Override
	public void removeMovie(Movie movie) {
		// TODO Auto-generated method stub
		movies.remove(movie);
	}

	@Override
	public Movie getMovie(String name) {
		// TODO Auto-generated method stub
		MovieImdb movie = null;
		Iterator<Movie> it = movies.iterator();
		while(it.hasNext()) {
			movie = (MovieImdb)it.next();
			if(movie.getName() != null) {
				if(movie.getName().equals(name)) {
					break;
				}
			}
		}
		return (Movie) movie;
	}
	
	@Override
	public boolean containMovie(String name) {
		return (getMovie(name) != null);
	}

	// TODO extract the database into a separate class ? YES Single responsibility principle...
	// the list could use a "store" which could be a database or a file or anything else...
	public void SaveMovies(String filepath) {
		
		try {
			CSVWriter writer = new CSVWriter(new FileWriter(filepath), ',');
			Iterator<Movie> itMovie = iterator();
			
			while(itMovie.hasNext()) {
				MovieImdb movieImdb = (MovieImdb)itMovie.next();
				
				// feed in your array (or convert your data to an array)
				String[] entries = new String[7];
			    entries[0] = movieImdb.getName();
			    entries[1] = String.valueOf(movieImdb.getYear());
			    entries[2] = movieImdb.getCountry();
			    entries[3] = movieImdb.getGenre();
			    entries[4] = String.valueOf(movieImdb.getRating());
			    entries[5] = movieImdb.getId();
			    if(movieImdb.getUrl() != null)
			    	entries[6] = movieImdb.getUrl().toString();
			    else
			    	entries[6] = "";
			    
		 	    writer.writeNext(entries);
			}

			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void LoadMovies(String filepath) {
		@SuppressWarnings("unchecked")
		ArrayList<Movie> backupList = (ArrayList<Movie>) movies.clone();
	
		try {
			CSVReader reader = new CSVReader(new FileReader(filepath));
			
		    String [] nextLine;
		    while ((nextLine = reader.readNext()) != null) {
		        // nextLine[] is an array of values from the line
		        MovieImdb newMovie = new MovieImdb(nextLine[0]);
		        newMovie.setYear(Integer.parseInt(nextLine[1]));
		        newMovie.setCountry(nextLine[2]);
		        newMovie.setGenre(nextLine[3]);
		        newMovie.setRating(Float.parseFloat(nextLine[4]));
		        newMovie.setId(nextLine[5]);
		        if(!nextLine[6].isEmpty()) {
		        	newMovie.setUrl(new URL(nextLine[6]));
		        }
		        movies.add(newMovie);
		    }
		    
		    reader.close();
		} catch (Exception e) {
			// If it fails, restore the current backup list
			movies = backupList;
			e.printStackTrace();
			System.out.println("Loading of movie CSV file " + filepath + ". Previous list is restored.");
		}
	}

	@Override
	public int getMovieQty() {
		return movies.size();
	}
	
	public String[] getMovieNames() {
		String[] movieNames = new String[movies.size()];
		
		for (int i = 0; i < movies.size(); i++) {
			MovieImdb movieImdb = (MovieImdb) movies.get(i);
			movieNames[i] = movieImdb.getName();
		}
		
		return movieNames;
	}
	
	// TODO revisit the whole MVC pattern.. there's is like 3-5 appraoch of doing it...
	// ask stack overflow...
	public Iterator<Movie> iterator() {        
        Iterator<Movie> iMovie = movies.iterator();
        return iMovie; 
    }
}
