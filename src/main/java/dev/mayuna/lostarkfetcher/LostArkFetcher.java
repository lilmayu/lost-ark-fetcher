package dev.mayuna.lostarkfetcher;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import dev.mayuna.lostarkfetcher.objects.api.*;
import dev.mayuna.lostarkfetcher.objects.api.other.LostArkNewsTag;
import dev.mayuna.lostarkfetcher.objects.api.other.LostArkRegion;
import dev.mayuna.lostarkfetcher.objects.api.other.LostArkServerStatus;
import dev.mayuna.lostarkfetcher.utils.LostArkConstants;
import dev.mayuna.lostarkfetcher.utils.LostArkUtil;
import dev.mayuna.simpleapi.APIRequest;
import dev.mayuna.simpleapi.Action;
import dev.mayuna.simpleapi.PathParameter;
import dev.mayuna.simpleapi.SimpleAPI;
import lombok.NonNull;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

public class LostArkFetcher extends SimpleAPI {

    /**
     * Returns empty string. To customize urls, use #setX() methods
     *
     * @return Empty string
     */
    @Override
    public @NonNull String getURL() {
        return "";
    }

    // https://forums.playlostark.com/c/official-news/official-news/53/l/latest.json?ascending=false

    public Action<LostArkServers> fetchServers() {
        return new Action<>(this, LostArkServers.class, new APIRequest.Builder()
                .setEndpoint(LostArkConstants.SERVER_STATUS_URL)
                .setMethod("GET")
                .build())
                .onDeserialization(httpResponse -> {
                    Document document = LostArkUtil.getDocument(httpResponse);

                    // == Regions == //
                    List<LostArkRegion> lostArkRegions = new LinkedList<>();
                    Elements regionTabs = document.getElementsByClass("ags-ServerStatus-content-tabs-tabHeading");

                    for (int i = 0; i < regionTabs.size(); i++) {
                        Element regionTab = regionTabs.get(i);
                        Attributes attributes = regionTab.attributes();
                        Element label = regionTab.getElementsByClass("ags-ServerStatus-content-tabs-tabHeading-label").first();

                        if (label == null) {
                            throw new RuntimeException("Could not find label!");
                        }

                        String regionName = label.text();
                        String dataIndexAttribute = attributes.get("data-index");

                        LostArkRegion lostArkRegion = LostArkRegion.get(regionName);

                        if (lostArkRegion == null) {
                            int dataIndex;
                            try {
                                dataIndex = NumberFormat.getInstance().parse(dataIndexAttribute).intValue();
                            } catch (ParseException parseException) {
                                throw new RuntimeException("Could not parse dataIndex attribute!", parseException);
                            }

                            lostArkRegion = LostArkRegion.get(dataIndex);

                            if (lostArkRegion == null) {
                                throw new RuntimeException("Could not parse region! Text: " + regionName);
                            }
                        }

                        if (!lostArkRegions.contains(lostArkRegion)) {
                            lostArkRegions.add(lostArkRegion);
                        }
                    }

                    // == Servers == //
                    List<LostArkServer> servers = new LinkedList<>();
                    Elements serverGroups = document.getElementsByClass("ags-ServerStatus-content-responses-response");

                    for (int i = 0; i < serverGroups.size(); i++) {
                        Element serverGroup = serverGroups.get(i);
                        Attributes attributes = serverGroup.attributes();

                        String dataIndexAttribute = attributes.get("data-index"); // Determines regions
                        int dataIndex;
                        try {
                            dataIndex = NumberFormat.getInstance().parse(dataIndexAttribute).intValue();
                        } catch (ParseException parseException) {
                            throw new RuntimeException("Could not parse dataIndex attribute!", parseException);
                        }
                        LostArkRegion lostArkRegion = LostArkRegion.get(dataIndex);

                        Elements serverList = serverGroup.getElementsByClass("ags-ServerStatus-content-responses-response-server");

                        for (int x = 0; x < serverList.size(); x++) {
                            Element serverElement = serverList.get(x);
                            Element serverNameElement = serverElement.getElementsByClass("ags-ServerStatus-content-responses-response-server-name").first();

                            if (serverNameElement == null) {
                                continue;
                            }

                            String serverName = serverNameElement.text();
                            LostArkServerStatus serverStatus = null;

                            for (LostArkServerStatus status : LostArkServerStatus.values()) {
                                if (!serverElement.getElementsByClass(status.getCSS()).isEmpty()) {
                                    serverStatus = status;
                                    break;
                                }
                            }

                            if (serverStatus == null) {
                                serverStatus = LostArkServerStatus.OFFLINE;
                            }

                            servers.add(new LostArkServer(serverName, lostArkRegion, serverStatus));
                        }
                    }

                    return new LostArkServers(servers);
                });
    }

    public Action<LostArkNewsTag[]> fetchNewsTags() {
        return new Action<>(this, LostArkNewsTag[].class, new APIRequest.Builder()
                .setEndpoint(LostArkConstants.NEWS_URL)
                .setMethod("GET")
                .build())
                .onDeserialization(httpResponse -> {
                    Document document = LostArkUtil.getDocument(httpResponse);

                    Elements newsTagElements = document.getElementsByClass("ags-NewsLandingPage-filter-popoverListItem");
                    LostArkNewsTag[] newsTags = new LostArkNewsTag[newsTagElements.size()];

                    for (int i = 0; i < newsTagElements.size(); i++) {
                        Element newsTag = newsTagElements.get(i);
                        newsTags[i] = new LostArkNewsTag(newsTag.text());
                    }

                    return newsTags;
                });
    }

    public Action<LostArkNews[]> fetchNews() {
        return fetchNews(null);
    }

    public Action<LostArkNews[]> fetchNews(LostArkNewsTag lostArkNewsTag) {
        String requestUrl;

        if (lostArkNewsTag != null) {
            requestUrl = lostArkNewsTag.getUrlWithQueryTag();
        } else {
            requestUrl = LostArkConstants.NEWS_URL;
        }

        return new Action<>(this, LostArkNews[].class, new APIRequest.Builder()
                .setEndpoint(requestUrl)
                .setMethod("GET")
                .build())
                .onDeserialization(httpResponse -> {
                    Document document = LostArkUtil.getDocument(httpResponse);

                    Elements newsSlotModules = document.getElementsByClass("ags-SlotModule");
                    LostArkNews[] lostArkNews = new LostArkNews[newsSlotModules.size()];

                    for (int i = 0; i < newsSlotModules.size(); i++) {
                        Element newsSlotModule = newsSlotModules.get(i);

                        String newsTitle;
                        String newsPublishDate;
                        LostArkNewsTag newsTag;
                        String newsUrl;
                        String newsDescription;
                        String newsThumbnailUrl;

                        newsTag = new LostArkNewsTag(LostArkUtil.getFirstElement(newsSlotModule, "ags-SlotModule-aboveImageBlogTag").text());
                        newsUrl = LostArkConstants.LOST_ARK_DOMAIN + LostArkUtil.getFirstElement(newsSlotModule, "ags-SlotModule-spacer").attr("href");
                        newsThumbnailUrl = "https:" + LostArkUtil.getFirstElement(newsSlotModule, "ags-SlotModule-imageContainer-image").attr("src");
                        newsPublishDate = LostArkUtil.getFirstElement(newsSlotModule, "ags-SlotModule-contentContainer-date").text();
                        newsTitle = LostArkUtil.getFirstElement(newsSlotModule, "ags-SlotModule-contentContainer-heading").text();
                        newsDescription = LostArkUtil.getFirstElement(newsSlotModule, "ags-SlotModule-contentContainer-text").ownText();

                        lostArkNews[i] = new LostArkNews(newsTitle, newsPublishDate, newsTag, newsUrl, newsDescription, newsThumbnailUrl);
                    }

                    return lostArkNews;
                });
    }

    public Action<LostArkForums> fetchForums(int forumCategory) {

        return new Action<>(this, LostArkForums.class, new APIRequest.Builder()
                .setEndpoint(LostArkConstants.FORUMS_URL)
                .addPathParameter(new PathParameter("forum_category", String.valueOf(forumCategory)))
                .setMethod("GET")
                .build());
    }

    public Action<LostArkForumTopic> fetchForumTopic(LostArkForums.TopicList.Topic topic) {

        return new Action<>(this, LostArkForumTopic.class, new APIRequest.Builder()
                .setEndpoint(topic.getJsonUrl())
                .setMethod("GET")
                .build());
    }
}
