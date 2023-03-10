package com.liboshuai.mall.admin.module.pms.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liboshuai.mall.admin.common.constants.DatePattern;
import com.liboshuai.mall.admin.compone.response.ResponseResult;
import com.liboshuai.mall.admin.module.pms.domain.dto.PmsProductAttributeValueES;
import com.liboshuai.mall.admin.module.pms.domain.dto.PmsProductES;
import com.liboshuai.mall.admin.module.pms.domain.entity.PmsProduct;
import com.liboshuai.mall.admin.module.pms.domain.entity.PmsProductAttributeValue;
import com.liboshuai.mall.admin.module.pms.mapper.PmsProductMapper;
import com.liboshuai.mall.admin.module.pms.service.PmsProductAttributeValueService;
import com.liboshuai.mall.admin.module.pms.service.PmsProductService;
import com.liboshuai.mall.admin.utils.DateUtil;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.ClearScrollRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchScrollRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.core.TimeValue;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.Scroll;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * ???????????? ???????????????
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Service
public class PmsProductServiceImpl extends ServiceImpl<PmsProductMapper, PmsProduct> implements PmsProductService {

    @Autowired
    private PmsProductMapper pmsProductMapper;

    @Autowired
    private PmsProductAttributeValueService pmsProductAttributeValueService;

    @Resource
    private RestHighLevelClient restHighLevelClient;

    private static final String INDEX_NAME = "product";
    private static final String TYPE_NAME = "_doc";

    /**
     * ????????????????????????????????????ES
     */
    @Override
    public boolean importAllProductToEs() {
        // ????????????product
        List<PmsProductES> esProductList = getPmsProductES();
        if (esProductList == null) {return true;}
        // ??????????????????
        BulkRequest bulkRequest = new BulkRequest();
        // ??????IndexRequest????????????
        esProductList.forEach(esProduct -> {
            IndexRequest indexRequest = new IndexRequest(INDEX_NAME, TYPE_NAME);
            indexRequest.id(esProduct.getId().toString()).source(JSONObject.toJSONString(esProduct), XContentType.JSON);
            bulkRequest.add(indexRequest);
        });
        try {
            BulkResponse response = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
            return Objects.equals(response.status(), RestStatus.OK);
        } catch (IOException ioException) {
            log.error("????????????????????????????????????ES??????: {}", ioException);
            return false;
        }
    }

    /**
     * es??????????????????
     */
    @Override
    public List<PmsProductES> testTermQuery() {
        // ??????????????????????????????termQuery ?????????????????????????????? boolean???int???double???string ???????????????????????? string ????????????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.termsQuery("brandName", "??????", "??????"));
        // ??????100???, ???????????????10?????????
        searchSourceBuilder.size(100);
        // ????????????????????????, ??????????????????????????????
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        // ????????????, ????????????????????????
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            log.error("es????????????????????????: {}", ioException);
            return null;
        }
        // ??????????????????????????????????????????????????????
        List<PmsProductES> pmsBrandDTOList = new ArrayList<>();
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            hits.forEach(hit -> pmsBrandDTOList.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return pmsBrandDTOList;
        }
        return null;
    }

    /**
     * es??????????????????
     */
    @Override
    public List<PmsProductES> testMatchAllQuery() {
        // ??????????????????
        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        // ????????????????????????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(matchAllQueryBuilder);
        // ????????????
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(3);
        // ????????????
        searchSourceBuilder.sort("createTime", SortOrder.DESC);
        // ????????????????????????, ??????????????????????????????
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        // ????????????, ????????????????????????
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            log.error("es????????????????????????:{}", ioException);
        }
        // ???????????????????????????????????????
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            List<PmsProductES> pmsProductESList = new ArrayList<>();
            hits.forEach(hit -> {
                pmsProductESList.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class));
            });
            return pmsProductESList;
        }
        return null;
    }

    /**
     * es??????????????????
     */
    @Override
    public List<PmsProductES> testMatchQuery(String name) {
        // ??????????????????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchQuery("name", name));
        // ????????????????????????, ??????????????????????????????
        SearchRequest searchRequest = new SearchRequest(TYPE_NAME);
        searchRequest.source(searchSourceBuilder);
        // ????????????, ????????????????????????
        SearchResponse searchResponse = null;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException ioException) {
            log.error("es????????????????????????: ", ioException);
            return null;
        }
        // ???????????????????????????????????????
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return pmsProductES;
        }
        return null;
    }

    /**
     * es??????????????????
     */
    @Override
    public ResponseResult<List<PmsProductES>> testMatchPhraseQuery(String name) {
        // ??????????????????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("name", name));
        // ????????????????????????, ??????????????????????????????
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        // ????????????, ????????????????????????
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es??????????????????: ", e);
            return ResponseResult.fail();
        }
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return ResponseResult.success(pmsProductES);
        }
        return ResponseResult.fail();
    }

    /**
     * es?????????????????????????????????
     */
    @Override
    public ResponseResult<List<PmsProductES>> testMatchMultiQuery(String query) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.multiMatchQuery(query, "name", "subTitle"));
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es?????????????????????????????????: ", e);
            return ResponseResult.fail();
        }
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return ResponseResult.success(pmsProductES);
        }
        return ResponseResult.fail();
    }

    /**
     * es???????????????
     */
    @Override
    public ResponseResult<List<PmsProductES>> testWildcardQuery(String name) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.wildcardQuery("name", name));
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es???????????????: ", e);
            return ResponseResult.fail();
        }
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return ResponseResult.success(pmsProductES);
        }
        return ResponseResult.fail();
    }

    /**
     * es????????????
     */
    @Override
    public ResponseResult<List<PmsProductES>> testFuzzQuery(String subTitle) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.fuzzyQuery("subTitle", subTitle).fuzziness(Fuzziness.AUTO));
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es????????????: ", e);
            return ResponseResult.fail();
        }
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return ResponseResult.success(pmsProductES);
        }
        return ResponseResult.fail();
    }

    /**
     * es????????????
     */
    @Override
    public ResponseResult<List<PmsProductES>> testPageQuery(int pageNum, int pageSize) {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        // ????????????
        searchSourceBuilder.from(pageNum);
        searchSourceBuilder.size(pageSize);
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es????????????: ", e);
            return ResponseResult.fail();
        }
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return ResponseResult.success(pmsProductES);
        }
        return ResponseResult.fail();
    }

    /**
     * es????????????
     */
    @Override
    public ResponseResult<List<PmsProductES>> testSortQuery() {
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery());
        searchSourceBuilder.sort("createTime", SortOrder.DESC);
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es????????????: ", e);
            return ResponseResult.fail();
        }
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return ResponseResult.success(pmsProductES);
        }
        return ResponseResult.fail();
    }

    /**
     * es????????????
     */
    @Override
    public ResponseResult<List<PmsProductES>> testScrollQuery(int pageNum, int pageSize) {
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery()).size(pageSize);
        String scrollId = null;
        SearchResponse scrollResponse = null;
        Scroll scroll = new Scroll(TimeValue.timeValueMinutes(2));
        ArrayList<String> scrollIds = new ArrayList<>();
        for (int i = 0; i < pageNum; i++) {
            if (i == 0) {
                searchRequest.scroll(scroll);
                SearchResponse response = null;
                try {
                    response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
                } catch (IOException e) {
                    log.error("es????????????: ", e);
                    return ResponseResult.fail(e.getMessage());
                }
                scrollId = response.getScrollId();
            }else {
                // ?????????????????????????????????????????????????????????????????????????????????????????????id???????????????????????????id???????????????????????????????????????id?????????
                SearchScrollRequest searchScrollRequest = new SearchScrollRequest(scrollId);
                searchScrollRequest.scroll(scroll);
                try {
                    scrollResponse = restHighLevelClient.scroll(searchScrollRequest, RequestOptions.DEFAULT);
                } catch (IOException e) {
                    log.error("es????????????: ", e);
                    return ResponseResult.fail(e.getMessage());
                }
                scrollId = scrollResponse.getScrollId();
            }
            scrollIds.add(scrollId);
        }
        ClearScrollRequest clearScrollRequest = new ClearScrollRequest();
        clearScrollRequest.scrollIds(scrollIds);
        try {
            restHighLevelClient.clearScroll(clearScrollRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("????????????????????????id??????: ", e);
            return ResponseResult.fail();
        }
        SearchHits hits = null;
        if (scrollResponse != null) {
            hits = scrollResponse.getHits();
        }
        ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
        if (hits != null) {
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
        }
        return ResponseResult.success(pmsProductES);
    }

    /**
     * es????????????
     */
    @Override
    public ResponseResult<List<PmsProductES>> testRangeQuery() {
        // ??????????????????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.rangeQuery("createTime").gte("2022-12-01"));
        // ?????????????????????????????????????????????????????????
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        // ???????????????????????????????????????
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es????????????: ", e);
            return ResponseResult.fail();
        }
        // ???????????????????????????????????????
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return ResponseResult.success(pmsProductES);
        }
        return ResponseResult.fail();
    }

    /**
     * es????????????
     */
    @Override
    public ResponseResult<List<PmsProductES>> testBoolQuery() {
        // ?????? Bool ???????????????
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        // ??????????????????
        boolQueryBuilder.must(QueryBuilders.matchQuery("name", "??????")).filter().add(QueryBuilders.rangeQuery("createTime").gte("2022-12-01"));
        // ????????????????????????
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(boolQueryBuilder);
        // ?????????????????????????????????????????????????????????
        SearchRequest searchRequest = new SearchRequest(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);
        // ???????????????????????????????????????
        SearchResponse searchResponse;
        try {
            searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            log.error("es????????????: ", e);
            return ResponseResult.fail();
        }
        // ???????????????????????????????????????
        if (Objects.equals(searchResponse.status(), RestStatus.OK)) {
            SearchHits hits = searchResponse.getHits();
            ArrayList<PmsProductES> pmsProductES = new ArrayList<>();
            hits.forEach(hit -> pmsProductES.add(JSONObject.parseObject(hit.getSourceAsString(), PmsProductES.class)));
            return ResponseResult.success(pmsProductES);
        }
        return ResponseResult.fail();
    }

    private List<PmsProductES> getPmsProductES() {
        List<PmsProduct> pmsProductList = this.list();
        if (CollectionUtils.isEmpty(pmsProductList)) {
            log.warn("????????????????????????????????????ES-????????????product??????");
            return null;
        }
        List<PmsProductES> esProductList = pmsProductList.stream().filter(Objects::nonNull).map(product -> {
            PmsProductES esProduct = new PmsProductES();
            BeanUtils.copyProperties(product, esProduct);
            esProduct.setCreateTime(Objects.isNull(product.getCreateTime()) ? null
                    : DateUtil.date2String(product.getCreateTime(), DatePattern.NORM_DATE_PATTERN));
            esProduct.setUpdateTime(Objects.isNull(product.getUpdateTime()) ? null
                    : DateUtil.date2String(product.getUpdateTime(), DatePattern.NORM_DATE_PATTERN));
            return esProduct;
        }).collect(Collectors.toList());
        // ????????????product???id??????
        List<Long> pmsProductIdList = pmsProductList.stream().map(PmsProduct::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pmsProductIdList)) {
            log.warn("????????????????????????????????????ES-??????product???id????????????");
            return null;
        }
        // ??????productId????????????PmsProductAttributeValue??????
        List<PmsProductAttributeValue> pmsProductAttributeValues = pmsProductAttributeValueService.lambdaQuery()
                .in(PmsProductAttributeValue::getProductId, pmsProductIdList).list();
        // ??????????????????PmsProductAttributeValue??????, ????????????ProductId????????????
        Map<Long, List<PmsProductAttributeValue>> productIdAndProductValueMap = pmsProductAttributeValues.stream()
                .collect(Collectors.groupingBy(PmsProductAttributeValue::getProductId));
        // ????????????Product????????????attrValueList??????
        for (Map.Entry<Long, List<PmsProductAttributeValue>> entry :
                productIdAndProductValueMap.entrySet()) {
            esProductList = esProductList.stream().peek(esProduct -> {
                if (Objects.equals(esProduct.getId(), entry.getKey())) {
                    List<PmsProductAttributeValueES> esProductAttributeValues = entry.getValue().stream()
                            .map(pmsProductAttributeValue -> {
                                PmsProductAttributeValueES esProductAttributeValue = new PmsProductAttributeValueES();
                                BeanUtils.copyProperties(pmsProductAttributeValue, esProductAttributeValue);
                                return esProductAttributeValue;
                            }).collect(Collectors.toList());
                    esProduct.setProductAttributeValues(esProductAttributeValues);
                }
            }).collect(Collectors.toList());
        }
        return esProductList;
    }

    /**
     * ??????id??????es????????????
     *
     * @param id ???????????????????????????
     */
//    @Override
//    public void deleteEsProductById(Long id) {
//        restHighLevelClient.deleteById(id);
//    }

    /**
     * ????????????????????????
     */
//    @Override
//    public int addProduct(List<AddProductReq> addProductReqs) {
//        List<PmsProduct> pmsProductList = addProductReqs.stream().map(addProductReq -> {
//            PmsProduct pmsProduct = new PmsProduct();
//            BeanUtils.copyProperties(addProductReq, pmsProduct);
//            if (!StringUtils.isEmpty(addProductReq.getPrice())) {
//                pmsProduct.setPrice(new BigDecimal(addProductReq.getPrice()));
//            }
//            return pmsProduct;
//        }).collect(Collectors.toList());
//        return pmsProductMapper.insertBatch(pmsProductList);
//    }

    /**
     * ??????????????????,???????????????,???????????????????????????????????????
     */
//    @Override
//    public Page<EsProduct> esProductSearch(EsSearchProduct esSearchProduct) {
//        if (Objects.isNull(esSearchProduct)) {
//            return null;
//        }
//        PageRequest pageRequest = PageRequest.of(esSearchProduct.getPageNum(), esSearchProduct.getPageSize());
//        return restHighLevelClient.findByNameOrSubTitleOrKeywords(esSearchProduct.getName(), esSearchProduct.getSubTitle(), esSearchProduct.getKeywords(), pageRequest);
//    }
}
