package dev.mayuna.lostarkfetcher.objects.api;

import dev.mayuna.lostarkfetcher.LostArkFetcher;
import dev.mayuna.lostarkfetcher.objects.api.other.LostArkNewsTag;
import dev.mayuna.simpleapi.APIResponse;
import lombok.Getter;

public class LostArkNews extends APIResponse<LostArkFetcher> {

    private @Getter String title;
    private @Getter String publishDate;
    private @Getter LostArkNewsTag tag;
    private @Getter String url;
    private @Getter String description;
    private @Getter String thumbnailUrl;

    public LostArkNews() {
    }

    public LostArkNews(String title, String publishDate, LostArkNewsTag tag, String url, String description, String thumbnailUrl) {
        this.title = title;
        this.publishDate = publishDate;
        this.tag = tag;
        this.url = url;
        this.description = description;
        this.thumbnailUrl = thumbnailUrl;
    }
}
