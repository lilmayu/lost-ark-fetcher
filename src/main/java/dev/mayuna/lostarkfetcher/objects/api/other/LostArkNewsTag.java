package dev.mayuna.lostarkfetcher.objects.api.other;

import dev.mayuna.lostarkfetcher.LostArkFetcher;
import dev.mayuna.lostarkfetcher.objects.api.LostArkNews;
import dev.mayuna.lostarkfetcher.utils.LostArkConstants;
import dev.mayuna.simpleapi.APIResponse;
import dev.mayuna.simpleapi.Action;
import lombok.Getter;

public class LostArkNewsTag extends APIResponse<LostArkFetcher> {

    private @Getter String displayName;

    public LostArkNewsTag() {
    }

    public LostArkNewsTag(String displayName) {
        this.displayName = displayName;
    }

    public String getQueryName() {
        return displayName.toLowerCase().replace(" ", "-");
    }

    public String getUrlWithQueryTag() {
        return LostArkConstants.NEWS_URL + "?tag=" + getQueryName();
    }

    public Action<LostArkNews[]> fetchNews() {
        return api.fetchNews(this);
    }
}
