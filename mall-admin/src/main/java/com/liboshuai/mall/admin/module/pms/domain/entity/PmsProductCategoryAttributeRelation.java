package com.liboshuai.mall.admin.module.pms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liboshuai.mall.admin.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * <p>
 * 产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类）
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Data
@TableName("pms_product_category_attribute_relation")
@ApiModel(value = "PmsProductCategoryAttributeRelation对象", description = "产品的分类和属性的关系表，用于设置分类筛选条件（只支持一级分类）")
public class PmsProductCategoryAttributeRelation extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("product_category_id")
    private Long productCategoryId;

    @TableField("product_attribute_id")
    private Long productAttributeId;

}




