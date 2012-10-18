package ca.movielist.MvcImplementation;

import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import ca.movielist.core.MovieUrlBuilder;
import ca.movielist.core.Movie;

public class TorrentZUrlBuilder implements MovieUrlBuilder {
	private static String HTTP = "http";
	private static String HOSTNAME = "torrentz.eu";
	private static String PATH = "/search";
	
	private static String QUERY_TAG = "f=";
	
	@Override
	public URL buildUrl(Movie movie) {
		
		// Build url string
		String urlParameters = movie.getName(); 		
		if(movie.getYear() != -1) {
			urlParameters = urlParameters + " " + String.valueOf(movie.getYear());
		}
		
		//urlParameters.replaceAll("/&/", "%26");
		URL url = null;

		// Build URL object
		try {
			urlParameters = URLEncoder.encode(urlParameters, "UTF-8");
			//URI uri = new URI(HTTP, HOSTNAME, PATH, QUERY_TAG + urlParameters, null);
			url = new URL(HTTP+"://"+HOSTNAME+PATH+"?"+QUERY_TAG+urlParameters);
			//theUrl = new URL(uri.toASCIIString());
			//theUrl = new URL(uri.toString());
		} catch (MalformedURLException e) {
			e.printStackTrace();
		//} catch (URISyntaxException e) {
			//e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return url;
	}
}
