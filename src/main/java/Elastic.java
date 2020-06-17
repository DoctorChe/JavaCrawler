import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.document.DocumentField;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

public class Elastic {

    private static String elasticsearchHost = "localhost";  // Хост elasticsearch
    private static int elasticsearchPort = 9200;  // Порт elasticsearch
    private static String dbIndex = "tut";  // Название индекса

    public static RestStatus addNews(Map<String, String> news) throws IOException {

        String id = news.get("id");
        String title = news.get("title");
        System.out.format("title: %s", title);

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort))
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort));

        System.out.format("client: %s", client);

        IndexResponse response = client.prepareIndex(dbIndex, "_doc", id)
                .setSource(jsonBuilder()
//                        .field("title", news.get("title"))
                        .field("title", title)
                        .field("pubDate", news.get("pubDate"))
                        .field("author", news.get("author"))
                        .field("text", news.get("text"))
                        .field("url", news.get("url"))
                        .endObject()
                )
                .get();
        System.out.format("Ответ: %s", response.status());
        client.close();
        return response.status();
    }

    public static GetResponse getNews(int id) throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort))
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort));

        GetResponse response = client.prepareGet("twitter", "_doc", String.valueOf(id)).get();

        client.close();

        return response;
    }

    public static QueryBuilder getAllNews() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort))
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort));

        QueryBuilder matchAllQuery = QueryBuilders.matchAllQuery();

        client.close();

        return matchAllQuery;
    }

    public static void getWords(int id) throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort))
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort));

        String text = getNews(id).getField("text").getValue();
        System.out.format("text: %s", text);



    }

    public static List<String> getWords() {
        return new ArrayList<>();
    }

    public static DeleteIndexRequest deleteIndex() throws UnknownHostException {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY)
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort))
                .addTransportAddress(new TransportAddress(InetAddress.getByName(elasticsearchHost), elasticsearchPort));

        DeleteIndexRequest request = new DeleteIndexRequest(dbIndex);

        client.close();

        return request;
    }

}
