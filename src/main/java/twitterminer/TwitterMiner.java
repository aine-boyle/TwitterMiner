package twitterminer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterMiner {

	public static void main(String[] args) {
		
		TwitterMiner twitterMiner = new TwitterMiner();
		twitterMiner.getTweet("Ireland");
	}
	
	public Connection getConnection () {
		Connection con = null;
		try {	
			String url ="jdbc:sqlserver://GANESHA\\SQLEXPRESS:1434;databaseName=4YP;integratedSecurity=true";
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
			con = DriverManager.getConnection(url);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return con;
	}
	
	public void getTweet (String keyword) {
		
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("xc1TZsYsiJUgu7OaetWoui0wc");
		cb.setOAuthConsumerSecret("ddSlwFH1QsEqIYYsQK5AGlXtWUkdWoOx7DPGoFX4d4R1pu4c76");
		cb.setOAuthAccessToken("265437614-aIQG0SgVsF9RKwWHKyzHwgQfh1w1gNKgnPOU1RJM");
		cb.setOAuthAccessTokenSecret("uNIUnajJ68aaERkN6ZxxOpSaooTMTMZPzCjYEzfA9eFJ1");

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		
		StatusListener listener = new StatusListener() {
	
			Connection con = getConnection();
			
			public void onException(Exception arg0) {
			}
	
			public void onDeletionNotice(StatusDeletionNotice arg0) {
			}
	
			public void onScrubGeo(long arg0, long arg1) {
			}
	
			public void onStatus(Status status) {
				User user = status.getUser();
				String username = status.getUser().getScreenName();
				String loc = user.getLocation();
				long tweetId = status.getId();
				String content = status.getText();
			
				String addTweet = "INSERT INTO twitter_data (username, location, tweetid, content) VALUES (?, ?, ?, ?)";
				try {
					PreparedStatement stmt = con.prepareStatement(addTweet);
					stmt.setString(1, username);
					stmt.setString(2, loc);
					stmt.setLong(3, tweetId);
					stmt.setString(4, content);
					stmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	
			public void onTrackLimitationNotice(int arg0) {
			}
	
			public void onStallWarning(StallWarning arg0) {
			}
		};
		
		FilterQuery fq = new FilterQuery();

		String keywords[] = { keyword };
		fq.track(keywords);

		twitterStream.addListener(listener);
		twitterStream.filter(fq);
	}
}