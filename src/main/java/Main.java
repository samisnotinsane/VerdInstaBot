import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramFollowRequest;
import org.brunocvcunha.instagram4j.requests.InstagramGetUserFollowersRequest;
import org.brunocvcunha.instagram4j.requests.InstagramLikeRequest;
import org.brunocvcunha.instagram4j.requests.InstagramTagFeedRequest;
import org.brunocvcunha.instagram4j.requests.payload.*;
import org.pmw.tinylog.Configurator;
import org.pmw.tinylog.Level;
import org.pmw.tinylog.Logger;
import org.pmw.tinylog.writers.FileWriter;
import org.pmw.tinylog.writers.Writer;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sameen on 16/07/2017.
 */
public class Main {

    private static String USERNAME = "verd.app";
    private static String PASSWORD = "startouch";
    private static int LIKE_TIME_INTERVAL = 120000;

    public static void main(String[] args) throws Exception {
        Configurator.defaultConfig()
                .writer(new FileWriter("verdInstaBotLog.txt"))
                .level(Level.INFO)
                .activate();

        Instagram4j insta = login();

        List<String> hashtags = Arrays.asList("earlyadopter", "futuretech");

        for(String hashtag : hashtags) {
            Logger.info("Scanning posts with hashtag: " + hashtag + ".");

            System.out.println("Scanning posts with hashtag: " + hashtag + ".");

            InstagramTagFeedRequest tagFeedRequest = new InstagramTagFeedRequest(hashtag);
            InstagramFeedResult tagFeed = insta.sendRequest(tagFeedRequest);

            for (InstagramFeedItem feedResult : tagFeed.getItems()) {

                InstagramUser user = feedResult.getUser(); // user of the post
                if (user.is_private()) {
                    Logger.info("User " + user.getUsername() + " is private.");
                    Logger.info("Following  " + user.getUsername() + ".");

                    System.out.println("User " + user.getUsername() + " is private.");
                    System.out.println("Following  " + user.getUsername() + ".");

                    insta.sendRequest(new InstagramFollowRequest(user.getPk())); // send follow request
                }

                Logger.info("Post " + feedResult.getId() + " has " +feedResult.getLike_count() + " likes.");

                System.out.println("Post " + feedResult.getId() + " has " +feedResult.getLike_count() + " likes.");

                List<InstagramUserSummary> likers = feedResult.getLikers();
                if (likers != null) {
                    for (InstagramUserSummary liker : likers) {
                        if (user.is_private()) {
                            Logger.info("User " + user.getUsername() + " is private.");
                            Logger.info("Following  " + user.getUsername() + ".");

                            System.out.println("User " + user.getUsername() + " is private.");
                            System.out.println("Following  " + user.getUsername() + ".");

                            insta.sendRequest(new InstagramFollowRequest(user.getPk())); // send follow request
                            Thread.sleep(5000);
                        }
                    }
                }


                Logger.info("Post ID: " + feedResult.getPk() + " by user: " + user.getUsername());

                System.out.println("Post ID: " + feedResult.getPk() + " by user: " + user.getUsername());

                /*
                 * Issue a like every 25 secs.
                 */
                Logger.info("Liking photo with caption: '" + feedResult.getCaption() + "'.");

                System.out.println("Liking photo with caption: '" + feedResult.getCaption() + "'.");

                insta.sendRequest(new InstagramLikeRequest(feedResult.getPk())); // like their post.

                // Wait to avoid detection...
                Logger.info("Sleeping to avoid detection...");

                System.out.println("Sleeping to avoid detection...");

                Thread.sleep(25000); // 120000

                // Followers of post user
                InstagramGetUserFollowersResult tagPostUserFollowers =
                        insta.sendRequest(new InstagramGetUserFollowersRequest(user.getPk()));

                List<InstagramUserSummary> followerUsers = tagPostUserFollowers.getUsers();

                for (InstagramUserSummary followerUser : followerUsers) {
                    Logger.info(followerUser.getUsername() + " follows " + user.getUsername() + ".");

                    System.out.println(followerUser.getUsername() + " follows " + user.getUsername() + ".");

                    if(followerUser.is_private()) {
                        Logger.info("User " + followerUser.getUsername() + " is private.");
                        Logger.info("Following user: " + followerUser.getUsername());

                        System.out.println("User " + followerUser.getUsername() + " is private.");
                        System.out.println("Following user: " + followerUser.getUsername());

                        insta.sendRequest(new InstagramFollowRequest(followerUser.getPk())); // send follow request
                    }

                    Logger.info("Sleeping to avoid detection...");

                    System.out.println("Sleeping to avoid detection...");

                    Thread.sleep(15000);
                }
            }
        }




    }

    private static Instagram4j login() throws Exception {
        // Login to instagram
        Instagram4j instagram = Instagram4j.builder().username(USERNAME).password(PASSWORD).build();
        instagram.setup();
        instagram.login();
        return instagram;
    }
}
