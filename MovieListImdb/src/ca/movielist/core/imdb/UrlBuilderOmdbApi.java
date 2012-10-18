package ca.movielist.core.imdb;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import ca.movielist.core.Movie;
import ca.movielist.core.MovieUrlBuilder;

public class UrlBuilderOmdbApi implements MovieUrlBuilder {
	private static String HTTP = "http";
	private static String HOSTNAME = "www.omdbapi.com";
	private static String PATH = "/";
	
	private static String QUERY_TAG = "t=";
	private static String YEAR_TAG = "y=";
	private static String TITLE_TAG = "i=";
	
	@Override
	public URL buildUrl(Movie movie) {
		
		MovieImdb movieImdb = (MovieImdb)movie;
		
		if(!movieImdb.getId().isEmpty()) {
			return buildUrlById(movieImdb);
		} else {
			return buildUrlByNameYear(movieImdb);
		}
	}
	
	private URL buildUrlById(MovieImdb movieImdb) {
		// Build url string
		String urlParameters = TITLE_TAG + movieImdb.getId(); 		
		
		// Build URL object
		URL theUrl = null;
		try {
			URI uri = new URI(HTTP, HOSTNAME, PATH, urlParameters, null);
			theUrl = new URL(uri.toASCIIString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return theUrl;
	}
	
	private URL buildUrlByNameYear(MovieImdb movieImdb) {
		// Build url string
		String urlParameters = QUERY_TAG + movieImdb.getName(); 		
		if(movieImdb.getYear() != -1) {
			urlParameters = urlParameters + "&" + YEAR_TAG + String.valueOf(movieImdb.getYear());
		}
		
		// Build URL object
		URL theUrl = null;
		try {
			URI uri = new URI(HTTP, HOSTNAME, PATH, urlParameters, null);
			theUrl = new URL(uri.toASCIIString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return theUrl;
	}

}
