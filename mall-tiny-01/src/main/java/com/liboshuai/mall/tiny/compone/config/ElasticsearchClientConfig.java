package com.liboshuai.mall.tiny.compone.config;


import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.json.jackson.JacksonJsonpMapper;
import co.elastic.clients.transport.ElasticsearchTransport;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: Bernardo
 * @Date: 2022/12/6 21:41
 * @Description:
 */

@Configuration
public class ElasticsearchClientConfig {

    private final ElasticsearchConfig elasticSearchConfig;

    public ElasticsearchClientConfig (ElasticsearchConfig elasticSearchConfig) {
        this.elasticSearchConfig = elasticSearchConfig;
    }

    @Bean
    public RestClient restClient() {

        // 拆分地址
        List<HttpHost> hostLists = new ArrayList<>();
        String[] hostArray = elasticSearchConfig.getAddress().split(",");
        for (String temp : hostArray) {
            String host = temp.split(":")[0];
            String port = temp.split(":")[1];
            hostLists.add(new HttpHost(host, Integer.parseInt(port), elasticSearchConfig.getSchema()));
        }

        // 转换成 HttpHost 数组
        HttpHost[] httpHost = hostLists.toArray(new HttpHost[]{});
        // 构建连接对象
        RestClientBuilder builder = RestClient.builder(httpHost);
        // 异步连接延时配置
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(elasticSearchConfig.getConnectTimeout());
            requestConfigBuilder.setSocketTimeout(elasticSearchConfig.getSocketTimeout());
            requestConfigBuilder.setConnectionRequestTimeout(elasticSearchConfig.getConnectionRequestTimeout());
            return requestConfigBuilder;
        });

        // 异步连接数配置
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(elasticSearchConfig.getMaxConnectNum());
            httpClientBuilder.setMaxConnPerRoute(elasticSearchConfig.getMaxConnectPerRoute());
            return httpClientBuilder;
        });

        return builder.build();
    }

    @Bean
    public ElasticsearchTransport elasticsearchTransport (RestClient restClient) {
        return new RestClientTransport(
                restClient, new JacksonJsonpMapper());
    }

    @Bean
    public ElasticsearchClient elasticsearchClient (ElasticsearchTransport transport) {
        return new ElasticsearchClient(transport);
    }
}
