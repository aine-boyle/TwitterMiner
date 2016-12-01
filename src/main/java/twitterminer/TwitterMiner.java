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
import twitter4j.conf.ConfigurationBuilder;

public class TwitterMiner {
	
	final static String keyword = "Ireland";

	public static void main(String[] args) {
		
		TwitterMiner twitterMiner = new TwitterMiner();
		twitterMiner.getTweet(keyword);
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
			
			public void onStatus(Status status) {
				String addTweet = "INSERT INTO twitter_data (username, location, tweetid, content) VALUES (?, ?, ?, ?)";
				try {
					PreparedStatement stmt = con.prepareStatement(addTweet);
					stmt.setString(1, status.getUser().getScreenName());
					stmt.setString(2, status.getUser().getLocation());
					stmt.setLong(3, status.getId());
					stmt.setString(4, status.getText());
					stmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
	
			public void onException(Exception arg0) {}
			
			public void onDeletionNotice(StatusDeletionNotice arg0) {}
	
			public void onScrubGeo(long arg0, long arg1) {}
			
			public void onTrackLimitationNotice(int arg0) {}
	
			public void onStallWarning(StallWarning arg0) {}
		};
		
		FilterQuery fq = new FilterQuery();

		String keywords[] = { keyword };
		fq.track(keywords);

		twitterStream.addListener(listener);
		twitterStream.filter(fq);
	}
}