package ca.movielist.core.imdb;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.movielist.core.Movie;
import ca.movielist.core.MovieLookup;
import ca.movielist.core.MovieUrlBuilder;

public class MovieLookupImdb implements MovieLookup {
	private MovieUrlBuilder urlBuilder, urlBuilder2;
	private ContentFetcher urlContentFetcher;
	
	public MovieLookupImdb() {
		urlBuilder = new UrlBuilderDeanClatWorthy();
		urlBuilder2 = new UrlBuilderOmdbApi();
		urlContentFetcher = new ContentFetcher();
	}
	
	public MovieLookupImdb(ContentFetcher contentFetcher) {
		urlBuilder = new UrlBuilderDeanClatWorthy();
		urlBuilder2 = new UrlBuilderOmdbApi();
		urlContentFetcher = contentFetcher;
	}
	
	@SuppressWarnings("unchecked")
	public void lookupInformations(Movie movie) {
		lookupInformationsFirstPass(movie);
		lookupInformationsSecondPass(movie);
	}

	public void lookupInformationsFirstPass(Movie movie) {
		URL url = urlBuilder.buildUrl(movie);
		if (url == null) return;
		
		String content = urlContentFetcher.fetchStringContent(url);
		if(content.isEmpty()) return;
		
		System.out.println(content);
		
		Map<String, Object> movieData = parseJsonContent(content);
		 
		if(movieData != null && movieData.size() > 0)
			updateMovieFromMap(movie, movieData);
	}
	
	public void lookupInformationsSecondPass(Movie movie) {
		URL url = urlBuilder2.buildUrl(movie);
		if (url == null) return;
		
		String content = urlContentFetcher.fetchStringContent(url);
		if(content.isEmpty()) return;
		
		System.out.println(content);
		
		Map<String, Object> movieData = parseJsonContent(content);
		 
		if(movieData != null && movieData.size() > 0)
			updateMovieFromMap2(movie, movieData);
	}
	
	private Map<String, Object> parseJsonContent(String content) {
		ObjectMapper mapper = new ObjectMapper();  
		Map<String, Object> movieData = null;
		try {
			movieData = mapper.readValue(content, Map.class);
			
			if (movieData.containsKey("error")) {
				movieData = null;
			}
		} catch (JsonParseException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} // TODO use the get from url directly ?
		return movieData;
	}

	private void updateMovieFromMap(Movie movie, Map<String, Object> movieData) {
		MovieImdb movieImdb = (MovieImdb)movie;
		try {
			String rating = (String) movieData.get("rating");
			movieImdb.setRating(Float.parseFloat(rating));
		} catch (Exception e) {
			System.out.println("Can't parse the 'rating' field in JSON");
		}

		try {
			String year = (String) movieData.get("year");
			movieImdb.setYear(Integer.parseInt(year));
		} catch (Exception e) {
			System.out.println("Can't parse the 'year' field in JSON");
		}
		
		try {
			String id = (String) movieData.get("imdbid");
			movieImdb.setId(id);
		} catch (Exception e) {
			System.out.println("Can't parse the 'imdbid' field in JSON");
		}
		
		try {
			String name = (String) movieData.get("title");
			movieImdb.setName(name);
		} catch (Exception e) {
			System.out.println("Can't parse the 'title' field in JSON");
		}
		
		try {
			String urls = (String) movieData.get("imdburl");
			URL url = new URL(urls);
			movieImdb.setUrl(url);
		} catch (Exception e) {
			System.out.println("Can't parse the 'imdburl' field in JSON");
		}
		
		try {
			String genres = (String) movieData.get("genres");
			movieImdb.setGenre(genres);
		} catch (Exception e) {
			System.out.println("Can't parse the 'genres' field in JSON");
		}
		
		try {
			String country = (String) movieData.get("country");
			movieImdb.setCountry(country);
		} catch (Exception e) {
			System.out.println("Can't parse the 'country' field in JSON");
		}
	}
	
	private void updateMovieFromMap2(Movie movie, Map<String, Object> movieData) {
		MovieImdb movieImdb = (MovieImdb)movie;
		try {
			String rating = (String) movieData.get("imdbRating");
			movieImdb.setRating(Float.parseFloat(rating));
		} catch (Exception e) {
			System.out.println("Can't parse the 'rating' field in JSON");
		}

		try {
			String year = (String) movieData.get("Year");
			movieImdb.setYear(Integer.parseInt(year));
		} catch (Exception e) {
			System.out.println("Can't parse the 'year' field in JSON");
		}
		
		try {
			String name = (String) movieData.get("Title");
			movieImdb.setName(name);
		} catch (Exception e) {
			System.out.println("Can't parse the 'title' field in JSON");
		}
		
		try {
			String urls = (String) movieData.get("Poster");
			URL url = new URL(urls);
			movieImdb.setImageUrl(url);
		} catch (Exception e) {
			System.out.println("Can't parse the 'imdburl' field in JSON");
		}
		
		try {
			String genres = (String) movieData.get("Genre");
			movieImdb.setGenre(genres);
		} catch (Exception e) {
			System.out.println("Can't parse the 'genres' field in JSON");
		}
	}
}
