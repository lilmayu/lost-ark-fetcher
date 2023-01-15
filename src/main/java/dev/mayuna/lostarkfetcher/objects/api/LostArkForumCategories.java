package dev.mayuna.lostarkfetcher.objects.api;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import dev.mayuna.simpleapi.deserializers.GsonDeserializer;
import lombok.Getter;
import lombok.NonNull;

public class LostArkForumCategories implements GsonDeserializer {

    private @Getter @SerializedName("category_list") CategoryList categoryList;

    @Override
    public @NonNull Gson getGson() {
        return new Gson();
    }

    public static class CategoryList {

        private @Getter @SerializedName("can_create_category") boolean canCreateCategory;
        private @Getter @SerializedName("can_create_topic") boolean canCreateTopic;

        private @Getter Category[] categories;

        public static class Category {

            private @Getter int id;
            private @Getter String name;
            private @Getter String color;
            private @Getter @SerializedName("text_color") String textColor;
            private @Getter String slug;
            private @Getter @SerializedName("topic_count") int topicCount;
            private @Getter @SerializedName("post_count") int postCount;
            private @Getter int position;
            private @Getter String description;
            private @Getter @SerializedName("description_text") String descriptionText;
            private @Getter @SerializedName("description_excerpt") String descriptionExcerpt;
            private @Getter @SerializedName("topic_url") String topicUrl;
            private @Getter @SerializedName("read_restricted") boolean readRestricted;
            //private @Getter Object permission;
            private @Getter @SerializedName("notification_level") int notificationLevel;
            private @Getter @SerializedName("topic_template") String topicTemplate;
            private @Getter @SerializedName("has_children") boolean hasChildren;
            private @Getter @SerializedName("sort_order") String sortOrder;
            //private @Getter @SerializedName("sort_ascending") Object sortAscending;
            private @Getter @SerializedName("show_subcategory_list") boolean showSubcategoryList;
            private @Getter @SerializedName("num_featured_topics") int numFeaturedTopics;
            private @Getter @SerializedName("default_view") String defaultView;
            private @Getter @SerializedName("subcategory_list_style") String subcategoryListStyle;
            private @Getter @SerializedName("default_top_period") String defaultTopPeriod;
            private @Getter @SerializedName("default_list_filter") String defaultListFilter;
            private @Getter @SerializedName("minimum_required_tags") int minimumRequiredTags;
            private @Getter @SerializedName("navigate_to_first_post_after_read") boolean navigateToFirstPostAfterRead;
            private @Getter @SerializedName("topics_day") int topicsDay;
            private @Getter @SerializedName("topics_week") int topicsWeek;
            private @Getter @SerializedName("topics_month") int topicsMonth;
            private @Getter @SerializedName("topics_year") int topicsYear;
            private @Getter @SerializedName("topics_all_time") int topicsAllTime;
            private @Getter @SerializedName("subcategory_ids") int[] subcategoryIds;
            private @Getter @SerializedName("uploaded_logo") UploadedLogo UploadedLogo;
            //private @Getter @SerializedName("uploaded_background") Object uploadedBackground;

            public static class UploadedLogo {

                private @Getter int id;
                private @Getter String url;
                private @Getter int width;
                private @Getter int height;
            }
        }
    }
}
