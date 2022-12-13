package com.liboshuai.mall.tiny.module.pms.domain.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.liboshuai.mall.tiny.module.pms.domain.entity.PmsProductAttributeValue;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * @Auther: Bernardo
 * @Date: 2022/12/13 23:24
 * @Description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PmsProductES implements Serializable {

    private static final long serialVersionUID = -6937669108955255767L;

    @TableField("brand_id")
    private Long brandId;

    @TableField("product_category_id")
    private Long productCategoryId;

    @TableField("feight_template_id")
    private Long feightTemplateId;

    @TableField("product_attribute_category_id")
    private Long productAttributeCategoryId;

    /**
     * 商品名称
     */
    private String name;

    @TableField("pic")
    private String pic;

    @ApiModelProperty("货号")
    @TableField("product_sn")
    private String productSn;

    @ApiModelProperty("上架状态：0->下架；1->上架")
    @TableField("publish_status")
    private Integer publishStatus;

    @ApiModelProperty("新品状态:0->不是新品；1->新品")
    @TableField("new_status")
    private Integer newStatus;

    @ApiModelProperty("推荐状态；0->不推荐；1->推荐")
    @TableField("recommand_status")
    private Integer recommandStatus;

    @ApiModelProperty("审核状态：0->未审核；1->审核通过")
    @TableField("verify_status")
    private Integer verifyStatus;

    @ApiModelProperty("排序")
    @TableField("sort")
    private Integer sort;

    @ApiModelProperty("销量")
    @TableField("sale")
    private Integer sale;

    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty("促销价格")
    @TableField("promotion_price")
    private BigDecimal promotionPrice;

    @ApiModelProperty("赠送的成长值")
    @TableField("gift_growth")
    private Integer giftGrowth;

    @ApiModelProperty("赠送的积分")
    @TableField("gift_point")
    private Integer giftPoint;

    @ApiModelProperty("限制使用的积分数")
    @TableField("use_point_limit")
    private Integer usePointLimit;

    @ApiModelProperty("副标题")
    @TableField("sub_title")
    private String subTitle;

    @ApiModelProperty("商品描述")
    @TableField("description")
    private String description;

    @ApiModelProperty("市场价")
    @TableField("original_price")
    private BigDecimal originalPrice;

    @ApiModelProperty("库存")
    @TableField("stock")
    private Integer stock;

    @ApiModelProperty("库存预警值")
    @TableField("low_stock")
    private Integer lowStock;

    @ApiModelProperty("单位")
    @TableField("unit")
    private String unit;

    @ApiModelProperty("商品重量，默认为克")
    @TableField("weight")
    private BigDecimal weight;

    @ApiModelProperty("是否为预告商品：0->不是；1->是")
    @TableField("preview_status")
    private Integer previewStatus;

    @ApiModelProperty("以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    @TableField("service_ids")
    private String serviceIds;

    /**
     * 关键字
     */
    private String keywords;

    @TableField("note")
    private String note;

    @ApiModelProperty("画册图片，连产品图片限制为5张，以逗号分割")
    @TableField("album_pics")
    private String albumPics;

    @TableField("detail_title")
    private String detailTitle;

    @TableField("detail_desc")
    private String detailDesc;

    @ApiModelProperty("产品详情网页内容")
    @TableField("detail_html")
    private String detailHtml;

    @ApiModelProperty("移动端网页详情")
    @TableField("detail_mobile_html")
    private String detailMobileHtml;

    @ApiModelProperty("促销开始时间")
    @TableField("promotion_start_time")
    private LocalDateTime promotionStartTime;

    @ApiModelProperty("促销结束时间")
    @TableField("promotion_end_time")
    private LocalDateTime promotionEndTime;

    @ApiModelProperty("活动限购数量")
    @TableField("promotion_per_limit")
    private Integer promotionPerLimit;

    @ApiModelProperty("促销类型：0->没有促销使用原价;1->使用促销价；2->使用会员价；3->使用阶梯价格；4->使用满减价格；5->限时购")
    @TableField("promotion_type")
    private Integer promotionType;

    /**
     * 品牌名称
     */
    private String brandName;

    @ApiModelProperty("商品分类名称")
    @TableField("product_category_name")
    private String productCategoryName;

    /**
     * 主键id
     */
    private Long id;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

//    List<PmsProductAttributeValue>
}
