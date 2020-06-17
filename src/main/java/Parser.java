import java.io.IOException;
import java.net.URL;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

    static String url = "https://news.tut.by/daynews/";
    private static String id1_author = "";
    private static int id1 = 0;
    private static int id2 = 0;

    private static Document getPage(String url) throws IOException {
        return Jsoup.parse(new URL(url), 3000);
    }

    private static List<String> getUrls(Document doc) {
        HashSet<String> urlHashSet = new HashSet<>();
        // выбираем все элементы <a class="entry__link">...
        Elements links = doc.select("a.entry__link");
        for (Element link : links) {
            // извлекаем атрибут href
            String url = link.attr("abs:href");
            // добавляем ссылку в HashSet для фильтрации повторов
            urlHashSet.add(url);
        }
        // конвертируем HashSet в List
        return new ArrayList<>(urlHashSet);
    }

    private static void parseNews(String url) throws IOException {
        Document doc = getPage(url);
        String title = getTitle(doc);
        String pubDate = getPubDate(doc);
        String author = getAuthor(doc);
        String text = getText(doc);

        Map<String, String> news = new HashMap<>();
        news.put("title", title);
        news.put("pubDate", pubDate);
        news.put("author", author);
        news.put("text", text);
        news.put("url", url);
        news.put("id", Integer.toString(text.hashCode()));

        // сохраняем новость в базе
        Elastic.addNews(news);

        if (id1_author.equals(""))
            id1_author = author;
        if (id1 == 0) {
            id1 = text.hashCode();
        }
        if (id1 != 0 & id2 == 0 & !id1_author.equals(author)) {
            id2 = text.hashCode();
        }
    }

    private static String getTitle(Document doc) {
        return doc.select("div.m_header").select("h1").first().text();
    }

    private static String getPubDate(Document doc) {
        // извлекаем атрибут datetime из элемента с атрибутом itemprop = datePublished
        String pubDate = doc.getElementsByAttributeValue("itemprop", "datePublished").attr("datetime");
        // переводим дату из строки в формат OffsetDateTime
        OffsetDateTime odt = OffsetDateTime.parse(pubDate);
        // форматируем вывод даты
        return odt.format(DateTimeFormatter.ISO_LOCAL_DATE);
    }

    private static String getAuthor(Document doc) {
        // извлекаем текст из элемента с атрибутом itemprop = name
        return doc.getElementsByAttributeValue("itemprop", "name").text();
    }

    private static String getText(Document doc) {
        // извлекаем текст из элементов p внутри элемента с id = article_body
        return doc.select("#article_body").select("p").text();
    }

    public static void run() throws IOException {
        Document page = getPage(url);
        List<String> urls = getUrls(page);
        for (String url : urls) {
            parseNews(url);
        }
    }

}
