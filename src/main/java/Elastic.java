import java.util.Map;

public class Elastic {

    String elasticsearchSocket = "http://0.0.0.0:9200";  // Сокет elasticsearch
    String dbIndex = "tut";  // Название индекса
    String dbTable = "news";  // Название таблицы

    public static void addNews(Map<String, String> news) {
        String title = news.get("title");
        String pubDate = news.get("pubDate");
        String author = news.get("author");
        String text = news.get("text");
        String url = news.get("url");
        String id = news.get("id");
    }

}
