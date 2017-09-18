package com.ey.spout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.lang.StringUtils;

import twitter4j.FilterQuery;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.User;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.URLEntity;
import twitter4j.conf.ConfigurationBuilder;
import backtype.storm.Config;
import backtype.storm.spout.SpoutOutputCollector;
import backtype.storm.task.TopologyContext;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseRichSpout;
import backtype.storm.tuple.Fields;
import backtype.storm.tuple.Values;
import backtype.storm.utils.Utils;

/**
 *  Spout that get the Twitter sample stream and emit them
 */

public class TwitterSampleSpout extends BaseRichSpout {
	
	private static final long serialVersionUID = -5878104600899840638L;
	private SpoutOutputCollector _collector = null;
	private LinkedBlockingQueue<Status> _statusQueue = null;
	private TwitterStream _twitterStream = null;
	//private Properties prop=new TupleHelper().loadPropertyFile();
	//prefer long over BigInteger, 
	//however need mechanism to handle overflow case (long.MAX_VALUE)
	private long _lostStatus;
	private long _receivedStatus;
	private long _emittedStatus;
	
	private String keywordsStr;
	
	/*private String keywordsStr;
	
	public TwitterSampleSpout(String[] keywordstoSearch){
		this.keywordsStr=keywordstoSearch;
	}*/
	
	public TwitterSampleSpout(String keywordtoSearch) {
		this.keywordsStr=keywordtoSearch;

	}

	public void open(@SuppressWarnings("rawtypes") Map conf, TopologyContext context,
		SpoutOutputCollector collector) {
		_collector = collector;
		_statusQueue = new LinkedBlockingQueue<Status>();
		ConfigurationBuilder cb=getConfigurationBuilder();
		
		_twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
		
	
		
		_lostStatus = 0;
		_receivedStatus = 0;
		_emittedStatus = 0;
		
		//use anonymous class to simplify the program
		//early observation: 300 to 400 tweets per 10 seconds (30 to 40 tps)
		//could be caused by 
		//1. overhead of twitter4J (?), event subscription
		//2. locking mechanism in LinkedBlockingQueue
		
		StatusListener listener = new StatusListener() {

			public void onException(Exception ex) {
				//do nothing, we are not interested in this event
			}

			//TODO: check whether StatusListener is threadsafe
			//If not, we need to lock the object to obtain "valid" count
			//Ultimate question: do we need the count?
			//Is stream rate important in this project?
			public void onStatus(Status status) {
				_receivedStatus++;
				
				
				if(!_statusQueue.offer(status)){
					_lostStatus++; 
				}			
			}

			public void onDeletionNotice(
					StatusDeletionNotice statusDeletionNotice) {
				//do nothing, we are not interested in this event
			}

			public void onTrackLimitationNotice(int numberOfLimitedStatuses) {
				//do nothing, we are not interested in this event				
			}

			public void onScrubGeo(long userId, long upToStatusId) {
				//do nothing, we are not interested in this event				
			}

			public void onStallWarning(StallWarning warning) {
				//do nothing, we are not interested in this event				
			}
		};
		
		_twitterStream.addListener(listener);
		
	
		/*String keywordsStr =
				"#expo2020|#expo2020dubai|#dubaiexpo2020|#dubai_expo|#dubai_expo_2020|#expo_2020";
		*/
		//String keywordsStr =System.getProperty("params");
		String[] keywordsToTrack = keywordsStr.split("\\|");
		
		FilterQuery filterQuery = new FilterQuery();
		filterQuery.track(keywordsToTrack);
		
		_twitterStream.filter(filterQuery);
	}

	public void nextTuple() {
		
		Status currentStatus = _statusQueue.poll();
		if(currentStatus != null){
			_emittedStatus++;
		
			_collector.emit(new Values(format(currentStatus)));
		} else {
			Utils.sleep(50);
		}
	}

	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("status"));
	}
	
	@Override
	public void close(){
		_twitterStream.shutdown();
		System.out.println(
				String.format("Tweets summary; rcvd = %d; " +
						"lost = %d; emitted = %d",
				_receivedStatus, _lostStatus, _emittedStatus));
	}
	
	@Override
	public Map<String, Object> getComponentConfiguration(){
		Config conf = new Config();
		conf.setMaxTaskParallelism(1);
		return conf;
	}
	
	@Override
	public void activate() {
		_twitterStream.sample();		
	};
	
	@Override
	public void deactivate() {
		_twitterStream.cleanUp();
	};
	
	public long getLostStatusCounter(){
		return _lostStatus;
	}
	
	public ConfigurationBuilder getConfigurationBuilder(){
		ConfigurationBuilder cb = new ConfigurationBuilder();
		
/*
		cb.setOAuthConsumerKey(prop.getProperty("oauth.consumerKey")).setOAuthConsumerSecret(prop.getProperty("oauth.consumerSecret")).setOAuthAccessTokenSecret(prop.getProperty("oauth.accessTokenSecret")).
		setOAuthAccessToken(prop.getProperty("oauth.accessToken")).setHttpProxyHost(prop.getProperty("http.proxyHost"))
		.setHttpProxyUser(prop.getProperty("http.proxyUser")).setHttpProxyPassword(prop.getProperty("http.proxyPassword")).setHttpProxyPort(8080);
*/

	/*cb.setOAuthConsumerKey(prop.getProperty("oauth.consumerKey")).setOAuthConsumerSecret(prop.getProperty("oauth.consumerSecret")).setOAuthAccessTokenSecret(prop.getProperty("oauth.accessTokenSecret")).
		setOAuthAccessToken(prop.getProperty("oauth.accessToken")).setHttpProxyHost("intriweb.ey.net")
		.setHttpProxyUser("ahalya.ms").setHttpProxyPassword("Password1").setHttpProxyPort(8080);
		
		
		
*/
		
		Calendar ca=Calendar.getInstance();
		Date dat=ca.getTime();
		
	
		
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(dat));
		System.out.println("in getconfig");
		cb.setOAuthConsumerKey("L19JwhrdTm6SCeqgbN9ELA").setOAuthConsumerSecret("Y8hx01vPzXtFtfkwXCv6CQi8bnPKYFtLBLd8ONmlxA").setOAuthAccessTokenSecret("eiR6F5MI7AbYbLrodbGOqZ412QL6I2yoV3DrcDnAO72gB").
		setOAuthAccessToken("2331132326-AdqtmWYRXRAzlMrD7LEg4c0v2lcIAhav80YHo40");
		
	

		
		
		

		
		//cb.setUser("amalbabu444").setPassword("silentwarrior");
	return cb;
		
	}
	
	
	
	static String format(Status status) {

		long createdAt = status.getCreatedAt().getTime();
		// User details of the status tweet
		User user = status.getUser();
		String name = user.getScreenName();
		long userId = user.getId();
		boolean verified = user.isVerified();

		// Tweet details
		long statusId = status.getId();
		// no api returning language now. This is a place holder
		String languageCode = "en";
		String statusStr = status.getText();

		HashtagEntity[] entities = status.getHashtagEntities();
		LinkedList<String> hashTags = new LinkedList<String>();
		for (HashtagEntity hashtagEntity : entities) {
			hashTags.add(hashtagEntity.getText());
		}
		String hashTagsStr = StringUtils.join(hashTags, '|');

		URLEntity[] urlEntities = status.getURLEntities();
		LinkedList<String> urls = new LinkedList<String>();
		for (URLEntity urlEntity : urlEntities) {
			String expandedURL = urlEntity.getExpandedURL();
			urls.add(expandedURL == null ? urlEntity.getURL() : expandedURL);
		}
		String urlsStr = StringUtils.join(urls, '|');

		GeoLocation geoLocation = status.getGeoLocation();
		String tweetGeo = "unknown";
		if (null != geoLocation) {
			StringUtils.join(new Double[] { geoLocation.getLatitude(),
					geoLocation.getLongitude() }, '|');
		}
		
		String userLocation = user.getLocation();
		userLocation = userLocation.trim().length() == 0 ? "unknown"
				: userLocation;

		boolean favorited = status.isFavorited();

		long retweetCount = status.getRetweetCount();

		return StringUtils.join(new Object[] { createdAt, userId, name,
				verified ? "1" : "0", statusId, languageCode, statusStr,
				hashTagsStr, urlsStr, tweetGeo, userLocation,
				favorited ? "1" : "0", String.valueOf(retweetCount) }, "|");
	}
}
