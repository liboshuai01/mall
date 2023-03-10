package com.liboshuai.mall.admin.module.pms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liboshuai.mall.admin.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Data
@TableName("pms_brand")
@ApiModel(value = "PmsBrand对象", description = "品牌表")
public class PmsBrand extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("产品名称")
    @TableField("name")
    private String name;

    @ApiModelProperty("首字母")
    @TableField("first_letter")
    private String firstLetter;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("是否为品牌制造商：0->不是；1->是")
    @TableField("factory_status")
    private Integer factoryStatus;

    @ApiModelProperty("展示状态")
    @TableField("show_status")
    private Integer showStatus;

    @ApiModelProperty("产品数量")
    @TableField("product_count")
    private Integer productCount;

    @ApiModelProperty("产品评论数量")
    @TableField("product_comment_count")
    private Integer productCommentCount;

    @ApiModelProperty("品牌logo")
    @TableField("logo")
    private String logo;

    @ApiModelProperty("专区大图")
    @TableField("big_pic")
    private String bigPic;

    @ApiModelProperty("品牌故事")
    @TableField("brand_story")
    private String brandStory;

}




