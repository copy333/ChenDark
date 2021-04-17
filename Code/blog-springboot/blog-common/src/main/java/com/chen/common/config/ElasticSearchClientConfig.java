package com.chen.common.config;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration //spring中的xml
public class ElasticSearchClientConfig {
    /**
     * 创建一个RestHighLevelClient对象
     * @return
     */
    @Bean
    public RestHighLevelClient restHighLevelClient(){
        RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("192.168.68.116",9200,"http"))
        );

        return client;
    }
}
