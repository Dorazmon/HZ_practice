package cn.com.tcsec.sdlmp.search.elasticsearch;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.elasticsearch.action.bulk.BackoffPolicy;
import org.elasticsearch.action.bulk.BulkProcessor;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.ByteSizeUnit;
import org.elasticsearch.common.unit.ByteSizeValue;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryAction;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ElasticSearchClient {

	// @Value("elasticSearch.host")
	private String host = "192.168.1.199";

	// @Value("elasticSearch.port")
	private int port = 9300;
	
	private final String INDEX = "OpenProject";

	public static void main(String[] args) throws Exception {
		ElasticSearchClient client = new ElasticSearchClient();
		List<String> list = new ArrayList<>();
		list.add("{\"kkkk\":\"12345\"}");

		client.update("google", list);
	}

	public void add(String project_name, List<String> data) throws Exception {
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

		BulkProcessor bulkProcessor = BulkProcessor.builder(client, new BulkProcessor.Listener() {
			@Override
			public void beforeBulk(long executionId, BulkRequest request) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, BulkResponse response) {
			}

			@Override
			public void afterBulk(long executionId, BulkRequest request, Throwable failure) {
			}
		}).setBulkActions(10000).setBulkSize(new ByteSizeValue(5, ByteSizeUnit.MB))
				.setFlushInterval(TimeValue.timeValueSeconds(5)).setConcurrentRequests(1)
				.setBackoffPolicy(BackoffPolicy.exponentialBackoff(TimeValue.timeValueMillis(100), 3)).build();

		for (String string : data) {
			XContentBuilder doc = XContentFactory.jsonBuilder().startObject().field("project_name", project_name)
					.field("postDate", new Date()).field("data", string).endObject();

			bulkProcessor.add(new IndexRequest(INDEX, "report").source(doc));
		}

		bulkProcessor.flush();

		client.admin().indices().prepareRefresh().get();

		// System.out.println(indexResponse.getId());
		client.close();
	}

	public void update(String project_name, List<String> data) throws Exception {
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

		BulkByScrollResponse response = DeleteByQueryAction.INSTANCE.newRequestBuilder(client)
				.filter(QueryBuilders.matchAllQuery()).source(INDEX).get();

		response.getDeleted();

//		add(project_name, data);

		client.close();
	}

	public Object search(String value) throws Exception {
		TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
		client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));

		SearchResponse scrollResp = client.prepareSearch().addSort("postDate", SortOrder.DESC)
				.setScroll(new TimeValue(60000)).setQuery(QueryBuilders.wildcardQuery("data", "*" + value + "*"))
				.setSize(100).get();
		Object obj = scrollResp.getHits().getHits();
		client.close();

		return obj;
	}
}
