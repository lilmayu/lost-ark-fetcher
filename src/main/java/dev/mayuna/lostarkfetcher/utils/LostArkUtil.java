package dev.mayuna.lostarkfetcher.utils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.http.HttpResponse;
import java.util.function.Consumer;

public class LostArkUtil {

    public static Document getDocument(HttpResponse<?> httpResponse) {
        if (!(httpResponse.body() instanceof String)) {
            throw new RuntimeException("Body is not string!");
        }

        try {
            return Jsoup.parse((String) httpResponse.body());
        } catch (Exception exception) {
            throw new RuntimeException("Could not deserialize servers HTML!", exception);
        }
    }

    public static Element getFirstElement(Element element, String className) {
        Elements slotModuleHeadings = element.getElementsByClass(className);
        if (!slotModuleHeadings.isEmpty()) {
            Element slotModuleHeading = slotModuleHeadings.first();

            if (slotModuleHeading == null) {
                throw new RuntimeException(className + " is null!");
            }

            return slotModuleHeading;
        } else {
            throw new RuntimeException(className + " is empty!");
        }
    }
}
