package dev.mayuna.lostarkfetcher.objects.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import dev.mayuna.lostarkfetcher.utils.LostArkConstants;
import dev.mayuna.simpleapi.deserializers.GsonDeserializer;
import lombok.Getter;
import lombok.NonNull;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LostArkForum implements GsonDeserializer {

    private @Getter User[] users;
    private @Getter @SerializedName("primary_groups") PrimaryGroup[] primaryGroups;
    private @Getter @SerializedName("flair_groups") FlairGroup[] flairGroups;
    private @Getter @SerializedName("topic_list") TopicList topicList;

    @Override
    public @NonNull Gson getGson() {
        return new Gson();
    }

    public static class User {

        private @Getter int id;
        private @Getter String username;
        private @Getter @SerializedName("avatar_template") String avatarTemplate;
        private @Getter @SerializedName("primary_group_name") String primaryGroupName;
        private @Getter @SerializedName("flair_name") String flairName;
        private @Getter boolean admin;
        private @Getter @SerializedName("trust_level") int trustLevel;
    }

    public static class PrimaryGroup {

        private @Getter int id;
        private @Getter String name;
    }

    public static class FlairGroup {

        private @Getter int id;
        private @Getter String name;
        private @Getter @SerializedName("flair_url") String flairUrl;
        private @Getter @SerializedName("flair_bg_color") String flairBgColor;
        private @Getter @SerializedName("flair_color") String flairColor;
    }

    public static class TopicList {

        private @Getter @SerializedName("can_create_topic") boolean canCreateTopic;
        private @Getter @SerializedName("more_topics_url") String moreTopicsUrl;
        private @Getter @SerializedName("per_page") int perPage;
        private @Getter Topic[] topics;

        /**
         * Parses {@link #moreTopicsUrl} and gets lasts element between slashesh, which is usually forum name in lowercase and space is replaced by dash<br>
         * For example: <code>/c/official-news/offizielle-neuigkeiten/14?page=1</code> returns <code>offizielle-neuigkeiten</code><br><br>
         * This will work only if field "moreTopicsUrl" is not null
         * @return Parsed {@link #moreTopicsUrl} or null if moreTopicsUrl is null
         */
        public String parseNameFromMoreTopicsUrl() {
            if (moreTopicsUrl == null) {
                return null;
            }

            Matcher matcher = Pattern.compile("(?<=/)[^/]+(?=/[^/]*$)").matcher(moreTopicsUrl);

            if (matcher.find()) {
                return matcher.group(matcher.groupCount());
            }

            return null;
        }

        public static class Topic {

            private @Getter int id;
            private @Getter String title;
            private @Getter @SerializedName("fancy_title") String fancyTitle;
            private @Getter String slug;
            private @Getter @SerializedName("posts_count") int postsCount;
            private @Getter @SerializedName("reply_count") int replyCount;
            private @Getter @SerializedName("highest_post_number") int highestPostNumber;
            private @Getter @SerializedName("image_url") String imageUrl;
            private @Getter @SerializedName("created_at") String createdAt;
            private @Getter @SerializedName("last_posted_at") String lastPostedAt;
            private @Getter boolean bumped;
            private @Getter @SerializedName("bumped_at") String bumpedAt;
            private @Getter String archetype;
            private @Getter boolean unseen;
            private @Getter boolean pinned;
            //private @Getter Object unpinned; // Don't know the object type
            private @Getter String excerpt;
            private @Getter boolean visible;
            private @Getter boolean closed;
            private @Getter boolean archived;
            //private @Getter Object bookmarked; // Don't know the object type
            //private @Getter Object linked; // Don't know the object type
            //private @Getter @SerializedName("tags_descriptions") Object tagsDescriptions; // Don't know the object type
            private @Getter int views;
            private @Getter @SerializedName("like_count") int likeCount;
            private @Getter @SerializedName("has_summary") boolean summary;
            private @Getter @SerializedName("last_poster_username") String lastPosterUsername;
            private @Getter @SerializedName("category_id") int categoryId;
            private @Getter @SerializedName("pinned_globally") boolean pinnedGlobally;
            //private @Getter @SerializedName("featured_link") Object featuredLink; // Don't know the object type
            private @Getter @SerializedName("first_tracked_post") FirstTrackedPost firstTrackedPost;
            private @Getter @SerializedName("has_accepted_answer") boolean acceptedAnswer;
            private @Getter Poster[] posters;

            public String getUrl() {
                return LostArkConstants.FORUM_TOPIC_URL.replace("{slug}", slug).replace("{post_id}", String.valueOf(id));
            }

            public String getJsonUrl() {
                return LostArkConstants.FORUM_TOPIC_JSON_URL.replace("{post_id}", String.valueOf(id));
            }

            public static class FirstTrackedPost {

                private @Getter String group;
                private @Getter @SerializedName("post_number") int postNumber;
            }

            public static class Poster {

                private @Getter String extras;
                private @Getter String description;
                private @Getter @SerializedName("user_id") int userId;
                private @Getter @SerializedName("primary_group_id") String primaryGroupId;
                private @Getter @SerializedName("flair_group_id") String flairGroupId;
            }
        }
    }
}
