package dev.mayuna.lostarkfetcher.objects.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import dev.mayuna.simpleapi.deserializers.GsonDeserializer;
import lombok.Getter;
import lombok.NonNull;

public class LostArkForumTopic implements GsonDeserializer {

    private @Getter @SerializedName("post_stream") PostStream postStream;

    @Override
    public @NonNull Gson getGson() {
        return new Gson();
    }

    public static class PostStream {

        private @Getter Post[] posts;

        public static class Post {

            private @Getter int id;
            private @Getter String username;
            private @Getter @SerializedName("created_at") String createdAt;
            private @Getter String cooked;

            public String getCookedClean() {
                return cooked.replaceAll("<.*?>", "").replaceAll("\n", " ").replaceAll(" {2,}", " ");
            }
        }
    }
}
