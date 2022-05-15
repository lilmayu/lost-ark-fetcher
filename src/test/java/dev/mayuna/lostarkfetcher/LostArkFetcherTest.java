package dev.mayuna.lostarkfetcher;

import dev.mayuna.lostarkfetcher.objects.api.LostArkForums;
import org.junit.Test;

public class LostArkFetcherTest {

    @Test
    public void testServers() {
        LostArkFetcher lostArkFetcher = new LostArkFetcher();

        lostArkFetcher.fetchForums(53).execute().thenAccept(lostArkForums -> {
           for (LostArkForums.TopicList.Topic topic : lostArkForums.getTopicList().getTopics()) {
               System.out.println("> " + topic.getTitle());

               lostArkFetcher.fetchForumTopic(topic).execute().thenAccept(lostArkForumTopic -> {
                    System.out.println("^ " + lostArkForumTopic.getPostStream().getPosts()[0].getCookedClean());
               });
           }
        });

    }

}
