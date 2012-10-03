package ca.movielist.core.imdb;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class ContentFetcher {
	public ContentFetcher() {
	}
	
	public String fetchContent(URL url) {
		System.out.println(url.toString());
		String content = "";
		try {
			InputStream is;
			is = url.openStream();
			int ptr = 0;
			StringBuffer buffer = new StringBuffer();
			while ((ptr = is.read()) != -1) {
			    buffer.append((char)ptr);
			}
			content = buffer.toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
}
