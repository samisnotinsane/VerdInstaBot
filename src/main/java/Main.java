import org.brunocvcunha.instagram4j.Instagram4j;
import org.brunocvcunha.instagram4j.requests.InstagramTagFeedRequest;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedItem;
import org.brunocvcunha.instagram4j.requests.payload.InstagramFeedResult;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sameen on 16/07/2017.
 */
public class Main {

    private static String USERNAME = "verd.app";
    private static String PASSWORD = "startouch";

    public static void main(String[] args) throws Exception {
        Instagram4j insta = login();

        List<String> hashtags = Arrays.asList("earlyadopter", "futuretech");

        for(String hashtag : hashtags) {
            InstagramTagFeedRequest tagFeedRequest = new InstagramTagFeedRequest(hashtag);
            InstagramFeedResult tagFeed = insta.sendRequest(tagFeedRequest);
            for (InstagramFeedItem feedResult : tagFeed.getItems()) {
                System.out.println("Post ID: " + feedResult.getPk());

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
