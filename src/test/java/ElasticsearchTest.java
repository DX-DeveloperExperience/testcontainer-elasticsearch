import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.testcontainers.elasticsearch.ElasticsearchContainer;

import java.io.IOException;

public class ElasticsearchTest {

    private static ElasticsearchContainer container;
    private static RestHighLevelClient client;

    @BeforeClass
    public static void startElasticsearch() {
        container = new ElasticsearchContainer("docker.elastic.co/elasticsearch/elasticsearch:7.2.0");
        container.start();

        client = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", container.getFirstMappedPort(), "http")));
    }

    @Test
    public void checkElasticsearchVersion() throws IOException {
        Assert.assertEquals("7.2.0", client.info(RequestOptions.DEFAULT).getVersion().getNumber());
    }

    @AfterClass
    public static void stopElasticsearch() throws IOException {
        if(client != null){
            client.close();
        }
        if(container != null){
            container.stop();
        }
    }
}