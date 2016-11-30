package twitterminer;

public class Tweet {
	private String username;
	private String location;
	private long tweetID;
	private String content;
	
	public Tweet () {}
	
	public Tweet(String username, String location, long tweetID, String content) {
		this.username = username;
		this.location = location;
		this.tweetID = tweetID;
		this.content = content;
	}
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public long getTweetID() {
		return tweetID;
	}
	public void setTweetID(long tweetID) {
		this.tweetID = tweetID;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
}
