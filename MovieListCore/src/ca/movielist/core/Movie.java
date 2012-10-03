package ca.movielist.core;

public interface Movie {
	
	public void setName(String name);
	public String getName();
	
	public void setRating(float rating);
	public float getRating();
	
	public void setYear(int year);
	public int getYear();
	
	public void setCountry(String country);
	public String getCountry();
	
	public void setGenre(String genre);
	public String getGenre();
}
