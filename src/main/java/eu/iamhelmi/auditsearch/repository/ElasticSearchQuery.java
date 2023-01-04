package eu.iamhelmi.auditsearch.repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.IndexQuery;
import org.springframework.data.elasticsearch.core.query.IndexQueryBuilder;
import org.springframework.stereotype.Repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.DeleteRequest;
import co.elastic.clients.elasticsearch.core.DeleteResponse;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import eu.iamhelmi.auditsearch.document.UserAccountDocument;

@Repository
public class ElasticSearchQuery {

	@Autowired
    private ElasticsearchClient elasticsearchClient;

    private final String indexName = "useraccount";
    @Autowired
    private ElasticsearchOperations elasticsearchOperations;

    //create single index
    public String createSingleProduct(UserAccountDocument product) {

        IndexQuery indexQuery = new IndexQueryBuilder()
                .withId(product.getId())
                .withObject(product).build();

        String documentId = elasticsearchOperations
                .index(indexQuery, IndexCoordinates.of(indexName));

        return documentId;
    }


    public String createOrUpdateDocument(UserAccountDocument userAccount) throws IOException {

        IndexResponse response = elasticsearchClient.index(i -> i
                .index(indexName)
                .id(userAccount.getId())
                .document(userAccount)
        );
        if (response.result().name().equals("Created")) {
            return new StringBuilder("Document has been successfully created.").toString();
        } else if (response.result().name().equals("Updated")) {
            return new StringBuilder("Document has been successfully updated.").toString();
        }
        return new StringBuilder("Error while performing the operation.").toString();
    }

    public UserAccountDocument getDocumentById(String id) throws IOException {
    	UserAccountDocument userAccount = null;
        GetResponse<UserAccountDocument> response = elasticsearchClient.get(g -> g
                        .index(indexName)
                        .id(id),
                        UserAccountDocument.class
        );

        if (response.found()) {
        	userAccount = response.source();
            System.out.println("UserAccount login " + userAccount.getLogin());
        } else {
            System.out.println("UserAccount not found");
        }

        return userAccount;
    }

    public String deleteDocumentById(String id) throws IOException {

        DeleteRequest request = DeleteRequest.of(d -> d.index(indexName).id(id));

        DeleteResponse deleteResponse = elasticsearchClient.delete(request);
        if (Objects.nonNull(deleteResponse.result()) && !deleteResponse.result().name().equals("NotFound")) {
            return new StringBuilder("UserAccount with id " + deleteResponse.id() + " has been deleted.").toString();
        }
        System.out.println("UserAccount not found");
        return new StringBuilder("UserAccount with id " + deleteResponse.id() + " does not exist.").toString();

    }

    public List<UserAccountDocument> searchAllDocuments() throws IOException {

        SearchRequest searchRequest = SearchRequest.of(s -> s.index(indexName));
        SearchResponse searchResponse = elasticsearchClient.search(searchRequest, UserAccountDocument.class);
        List<Hit> hits = searchResponse.hits().hits();
        List<UserAccountDocument> userAccounts = new ArrayList<>();
        for (Hit object : hits) {

            System.out.print(((UserAccountDocument) object.source()));
            userAccounts.add((UserAccountDocument) object.source());
            System.out.print(((UserAccountDocument) object.source()).getId());

        }
        return userAccounts;
    }
}
