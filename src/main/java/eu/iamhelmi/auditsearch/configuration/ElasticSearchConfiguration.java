package eu.iamhelmi.auditsearch.configuration;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;

@Configuration
public class ElasticSearchConfiguration {
	
	@Value(value = "${elasticsearch.server}")
    private String elasticsearchServer;
	
	@Value(value = "${elasticsearch.port}")
    private int elasticsearchPort;

	@Bean
	public RestClient getRestClient() {
		RestClient restClient = RestClient.builder(new HttpHost(elasticsearchServer, elasticsearchPort)).build();
		return restClient;
	}

	@Bean
	public ElasticsearchTransport getElasticsearchTransport() {
		return new RestClientTransport(getRestClient(), new JacksonJsonpMapper());
	}

	@Bean
	public ElasticsearchClient getElasticsearchClient() {
		ElasticsearchClient client = new ElasticsearchClient(getElasticsearchTransport());
		
		return client;
	}
}
