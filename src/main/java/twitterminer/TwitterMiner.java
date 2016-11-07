package twitterminer;

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
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true);
		cb.setOAuthConsumerKey("xc1TZsYsiJUgu7OaetWoui0wc");
		cb.setOAuthConsumerSecret("ddSlwFH1QsEqIYYsQK5AGlXtWUkdWoOx7DPGoFX4d4R1pu4c76");
		cb.setOAuthAccessToken("265437614-aIQG0SgVsF9RKwWHKyzHwgQfh1w1gNKgnPOU1RJM");
		cb.setOAuthAccessTokenSecret("uNIUnajJ68aaERkN6ZxxOpSaooTMTMZPzCjYEzfA9eFJ1");

		TwitterStream twitterStream = new TwitterStreamFactory(cb.build()).getInstance();

		StatusListener listener = new StatusListener() {

			public void onException(Exception arg0) {
				// TODO Auto-generated method stub
			}

			public void onDeletionNotice(StatusDeletionNotice arg0) {
				// TODO Auto-generated method stub
			}

			public void onScrubGeo(long arg0, long arg1) {
				// TODO Auto-generated method stub
			}

			public void onStatus(Status status) {
				User user = status.getUser();
				// gets Username
				String username = status.getUser().getScreenName();
				System.out.println("Username: " + username);
				String profileLocation = user.getLocation();
				System.out.println("Location: " + profileLocation);
				long tweetId = status.getId();
				System.out.println("Tweet ID: " + tweetId);
				String content = status.getText();
				System.out.println("Tweet: " + content + "\n");
			}

			public void onTrackLimitationNotice(int arg0) {
				// TODO Auto-generated method stub
			}

			public void onStallWarning(StallWarning arg0) {
				// TODO Auto-generated method stub
			}
		};

		FilterQuery fq = new FilterQuery();

		String keywords[] = { "ireland" };
		fq.track(keywords);

		twitterStream.addListener(listener);
		twitterStream.filter(fq);
	}
}