package ca.movielist.core.imdb;

import java.io.Serializable;
import java.net.URL;

import ca.movielist.core.Movie;
// TODO rename to Imdb
public class MovieImdb implements Movie, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected String name;
	private float rating = -1;
	private int year = -1;
	private String country = "";
	private String genre = "";
	private String id = "";
	private URL imdbUrl = null;
	
	public MovieImdb(String name) { // TODO redefine where movie and movie imdb differs...
		this.setName(name);
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override	
	public void setRating(float rating) {
		this.rating = rating;
	}
	
	@Override
	public float getRating() {
		return rating;
	}

	@Override
	public void setYear(int year) {
		this.year = year;
	}
	
	@Override
	public int getYear() {
		return year;
	}

	public void setId(String id) {
		this.id = id;
	}
	
	public String getId() {
		return id;
	}

	public void setUrl(URL imdbUrl) {
		this.imdbUrl = imdbUrl;
	}
	
	public URL getUrl() {
		return imdbUrl;
	}

	@Override
	public void setCountry(String country) {
		this.country = country;
		
	}

	@Override
	public String getCountry() {
		return country;
	}

	@Override
	public void setGenre(String genre) {
		this.genre = genre;
	}

	@Override
	public String getGenre() {
		return genre;
	}
}
