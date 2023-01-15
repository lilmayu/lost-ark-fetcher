package dev.mayuna.lostarkfetcher;

import dev.mayuna.lostarkfetcher.objects.api.LostArkForum;
import dev.mayuna.lostarkfetcher.objects.api.LostArkForumCategories;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class LostArkFetcherTest {

    @Test
    public void testServers() {
        LostArkFetcher lostArkFetcher = new LostArkFetcher();

        Map<Integer, String> map = new HashMap<>();
        map.put(0, "");

        lostArkFetcher.fetchNewsTags().execute().whenComplete((lostArkNewsTags, throwable) -> {
            Arrays.stream(lostArkNewsTags).forEach(newsTag -> {
                print(newsTag.getDisplayName());
            });
        });

        lostArkFetcher.fetchForumCategories().execute().whenComplete((lostArkForumCategories, exception) -> {
            for (LostArkForumCategories.CategoryList.Category category : lostArkForumCategories.getCategoryList().getCategories()) {
                print(">> Main Category ID: " + category.getId() + " (" + category.getName() + ")");

                Arrays.stream(category.getSubcategoryIds()).forEach(id -> {
                    AtomicReference<String> message = new AtomicReference<>("> Sub category ID: " + id + " (");
                    lostArkFetcher.fetchForum(id).execute().whenComplete((lostArkForum, throwable) -> {
                        try {
                            message.set(message.get() + lostArkForum.getTopicList().parseNameFromMoreTopicsUrl() + ")");
                        } catch (Exception exception1) {
                            exception1.printStackTrace();
                        }
                    });
                    print(message.get());
                });
            }
        });
    }

    private void print(String s) {
        System.out.println(s);
    }
}
