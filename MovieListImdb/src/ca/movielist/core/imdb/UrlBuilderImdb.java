package ca.movielist.core.imdb;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import ca.movielist.core.Movie;
import ca.movielist.core.MovieUrlBuilder;

public class UrlBuilderImdb implements MovieUrlBuilder {

	private static String HTTP = "http";
	private static String HOSTNAME = "www.deanclatworthy.com";
	private static String PATH = "/imdb/";
	
	private static String QUERY_TAG = "q=";
	private static String YEAR_TAG = "year=";
	
	@Override
	public URL buildUrl(Movie movie) {
		
		// Build url string
		String urlParameters = QUERY_TAG + movie.getName(); 		
		if(movie.getYear() != -1) {
			urlParameters = urlParameters + "&" + YEAR_TAG + String.valueOf(movie.getYear());
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
