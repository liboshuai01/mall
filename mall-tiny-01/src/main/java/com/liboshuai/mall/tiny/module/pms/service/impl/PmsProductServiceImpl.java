package com.liboshuai.mall.tiny.module.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liboshuai.mall.tiny.module.pms.domain.entity.PmsProduct;
import com.liboshuai.mall.tiny.module.pms.domain.entity.PmsProductAttributeValue;
import com.liboshuai.mall.tiny.module.pms.domain.req.AddProductReq;
import com.liboshuai.mall.tiny.module.pms.domain.req.EsSearchProduct;
import com.liboshuai.mall.tiny.module.pms.mapper.PmsProductMapper;
import com.liboshuai.mall.tiny.module.pms.service.PmsProductAttributeValueService;
import com.liboshuai.mall.tiny.module.pms.service.PmsProductService;
import com.liboshuai.mall.tiny.nosql.elasticsearch.document.EsProduct;
import com.liboshuai.mall.tiny.nosql.elasticsearch.document.EsProductAttributeValue;
import com.liboshuai.mall.tiny.nosql.elasticsearch.repository.EsProductRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 商品信息 服务实现类
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

    @Autowired
    private EsProductRepository esProductRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    /**
     * 创建商品es索引和类型
     */
    @Override
    public void createEsIndexType() {
        elasticsearchTemplate.createIndex(EsProduct.class);
        elasticsearchTemplate.putMapping(EsProduct.class);
    }

    /**
     * 从数据库中导入所有商品到ES
     */
    @Override
    public int importAllProductToEs() {
        // 清空之前es中的数据
        esProductRepository.deleteAll();
        // 查询全部product
        List<PmsProduct> pmsProductList = this.list();
        if (CollectionUtils.isEmpty(pmsProductList)) {
            log.warn("从数据库中导入所有商品到ES-查询全部product为空");
            return 0;
        }
        List<EsProduct> esProductList = pmsProductList.stream().filter(Objects::nonNull).map(product -> {
            EsProduct esProduct = new EsProduct();
            BeanUtils.copyProperties(product, esProduct);
            return esProduct;
        }).collect(Collectors.toList());
        // 拿到所有product的id集合
        List<Long> pmsProductIdList = pmsProductList.stream().map(PmsProduct::getId).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(pmsProductIdList)) {
            log.warn("从数据库中导入所有商品到ES-所有product的id集合为空");
            return 0;
        }
        // 根据productId集合查询PmsProductAttributeValue集合
        List<PmsProductAttributeValue> pmsProductAttributeValues = pmsProductAttributeValueService.lambdaQuery()
                .in(PmsProductAttributeValue::getProductId, pmsProductIdList).list();
        // 拿到查询到的PmsProductAttributeValue集合, 然后根据ProductId进行分组
        Map<Long, List<PmsProductAttributeValue>> productIdAndProductValueMap = pmsProductAttributeValues.stream()
                .collect(Collectors.groupingBy(PmsProductAttributeValue::getProductId));
        // 遍历设置Product集合中的attrValueList属性
        for (Map.Entry<Long, List<PmsProductAttributeValue>> entry :
                productIdAndProductValueMap.entrySet()) {
            esProductList = esProductList.stream().peek(esProduct -> {
                if (Objects.equals(esProduct.getId(), entry.getKey())) {
                    List<EsProductAttributeValue> esProductAttributeValues = entry.getValue().stream()
                            .map(pmsProductAttributeValue -> {
                                EsProductAttributeValue esProductAttributeValue = new EsProductAttributeValue();
                                BeanUtils.copyProperties(pmsProductAttributeValue, esProductAttributeValue);
                                return esProductAttributeValue;
                            }).collect(Collectors.toList());
                    esProduct.setAttrValueList(esProductAttributeValues);
                }
            }).collect(Collectors.toList());
        }
        Iterable<EsProduct> esProductIterable = esProductRepository.saveAll(esProductList);
        Iterator<EsProduct> iterator = esProductIterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            iterator.next();
            result++;
        }
        return result;
    }

    /**
     * 根据id删除es中的商品
     *
     * @param id 成功删除的商品数量
     */
    @Override
    public void deleteEsProductById(Long id) {
        esProductRepository.deleteById(id);
    }

    /**
     * 批量添加商品信息
     */
    @Override
    public int addProduct(List<AddProductReq> addProductReqs) {
        List<PmsProduct> pmsProductList = addProductReqs.stream().map(addProductReq -> {
            PmsProduct pmsProduct = new PmsProduct();
            BeanUtils.copyProperties(addProductReq, pmsProduct);
            if (!StringUtils.isEmpty(addProductReq.getPrice())) {
                pmsProduct.setPrice(new BigDecimal(addProductReq.getPrice()));
            }
            return pmsProduct;
        }).collect(Collectors.toList());
        return pmsProductMapper.insertBatch(pmsProductList);
    }

    /**
     * 根据商品名称,商品副标题,商品关键字分页搜索商品信息
     */
    @Override
    public Page<EsProduct> esProductSearch(EsSearchProduct esSearchProduct) {
        if (Objects.isNull(esSearchProduct)) {
            return null;
        }
        PageRequest pageRequest = PageRequest.of(esSearchProduct.getPageNum(), esSearchProduct.getPageSize());
        return esProductRepository.findByNameOrSubTitleOrKeywords(esSearchProduct.getName(), esSearchProduct.getSubTitle(), esSearchProduct.getKeywords(), pageRequest);
    }
}
