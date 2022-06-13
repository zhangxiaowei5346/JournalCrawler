package ai.zhuanzhi.webcollector.NSTRS;


import com.mongodb.*;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.*;

public class TopicTagging {

    public static <K, V> K getKey(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public static void main(String[] args) {

        MongoClientOptions options = MongoClientOptions.builder()
                .maxConnectionLifeTime(1000 * 3600 * 10000)
                .maxConnectionIdleTime(1000 * 3600 * 10000)
                .readPreference(ReadPreference.secondaryPreferred())
                .build();

        MongoClient mongoClient = new MongoClient(new ServerAddress("122.11.50.27", 27017), options);


        //TODO: get topics
        Bson query = Filters.eq("isTech", true);
        Bson projection = Projections.fields(Projections.include("_id", "name", "alias"));

        ArrayList<Bson> aggregateQuery = new ArrayList<>();

        aggregateQuery.add(Aggregates.match(query));
        aggregateQuery.add(Aggregates.project(projection));

        AggregateIterable<Document> topics = mongoClient.getDatabase("zhuanzhi").getCollection("topic1.0")
                .aggregate(aggregateQuery);

        HashMap<String, String> topicsId = new HashMap<>();

        for (Document topic : topics) {
            String id = topic.getString("_id");
            String name = topic.getString("name");
            topicsId.put(name, id);
            ArrayList<String> alias = topic.get("alias", ArrayList.class);
            if (alias != null && !alias.isEmpty()) {
                for (String alia : alias) {
                    if (!alia.equals(name)) {
                        topicsId.put(alia, "A" + id);
                    }
                }
            }
        }


        //TODO: get documents
        query = Filters.eq("source", "autofund");
        projection = Projections.fields(Projections.include("_id", "obj"));

        aggregateQuery = new ArrayList<>();
        aggregateQuery.add(Aggregates.match(query));
        aggregateQuery.add(Aggregates.project(projection));

        AggregateIterable<Document> funds = mongoClient.getDatabase("zhuanzhi").getCollection("document")
                .aggregate(aggregateQuery);


        for (Document fund : funds) {
            String fundId = fund.getString("_id");
            Document fundInfo = fund.get("obj", Document.class);
            String keywordsCn = fundInfo.getString("keywordsCn");
            String[] keywords = keywordsCn.split("；|; |;");
            ArrayList<Document> topicKTM = new ArrayList<>();
            for (String keyword : keywords) {
                if (keyword.equals("")) {
                    continue;
                }
                if (topicsId.containsKey(keyword)) {
                    String topicId = topicsId.get(keyword);
                    if (topicId.charAt(0) == 'A') {
                        keyword = getKey(topicsId, topicId.substring(1));
                        topicId = topicsId.get(keyword);
                    }
                    Document document = new Document();
                    document.append("id", topicId)
                            .append("word", keyword)
                            .append("source", "topic")
                            .append("weight", 1.0);
                    topicKTM.add(document);
                }
            }
            query = new Document().append("_id", fundId);
            try {
                if (topicKTM.size() > 0) {
                    mongoClient.getDatabase("zhuanzhi").getCollection("document")
                            .updateOne(query, Updates.set("topicKTM", topicKTM));
                    System.out.println("update successfully on " + fundId);
                }
            } catch (MongoException me) {
                System.err.println("Unable to update due to an error: " + me);
            }
        }

        mongoClient.close();
    }
}
