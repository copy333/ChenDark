package com.chen.context;

import com.chen.context.service.BlBlogService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;

import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class BlogContextApplicationTests {

    @Autowired
    private BlBlogService blBlogService;

    @Autowired
    @Qualifier(value = "restHighLevelClient")
    private RestHighLevelClient client;

    /**
     * 测试索引(index)的创建 Request
     */
    @Test
    void testCreateIndex() throws IOException {
        //1.创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("hhhh");
        //2.RestHighLevelClient客户端执行创建请求，并返回请求响应
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);


        System.out.println(createIndexResponse);

    }

    @Test
    void contextLoads() {
        //先查询数据获取到该文章的文档数据
        SearchRequest searchRequest = new SearchRequest("blog");
        //构建搜索条件
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //查询条件-完全匹配，博客文章id
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("blogId", "1362649933435932673");
        searchSourceBuilder.query(termQueryBuilder);

        searchSourceBuilder.timeout(new TimeValue(60)); //设置超时

        //将查询请求放入请求
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = null;

        try {
            searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(searchResponse.toString());

        for (SearchHit documentFields : searchResponse.getHits().getHits()){
            System.out.println(documentFields.getId());
        }
    }

}
