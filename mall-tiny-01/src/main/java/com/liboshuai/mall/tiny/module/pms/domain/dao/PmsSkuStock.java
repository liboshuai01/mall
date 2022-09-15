package com.liboshuai.mall.tiny.module.pms.domain.dao;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import java.io.Serializable;
    import java.math.BigDecimal;
    import java.time.LocalDateTime;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;

/**
* <p>
    * sku的库存
    * </p>
*
* @author liboshuai
* @since 2022-09-16
*/
    @Data
    @TableName("pms_sku_stock")
    @ApiModel(value = "PmsSkuStock对象", description = "sku的库存")
    public class PmsSkuStock implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        @TableField("product_id")
    private Long productId;

            @ApiModelProperty("sku编码")
        @TableField("sku_code")
    private String skuCode;

        @TableField("price")
    private BigDecimal price;

            @ApiModelProperty("库存")
        @TableField("stock")
    private Integer stock;

            @ApiModelProperty("预警库存")
        @TableField("low_stock")
    private Integer lowStock;

            @ApiModelProperty("销售属性1")
        @TableField("sp1")
    private String sp1;

        @TableField("sp2")
    private String sp2;

        @TableField("sp3")
    private String sp3;

            @ApiModelProperty("展示图片")
        @TableField("pic")
    private String pic;

            @ApiModelProperty("销量")
        @TableField("sale")
    private Integer sale;

            @ApiModelProperty("单品促销价格")
        @TableField("promotion_price")
    private BigDecimal promotionPrice;

            @ApiModelProperty("锁定库存")
        @TableField("lock_stock")
    private Integer lockStock;

            @ApiModelProperty("创建用户")
        @TableField("create_user")
    private String createUser;

            @ApiModelProperty("创建时间")
        @TableField("create_time")
    private LocalDateTime createTime;

            @ApiModelProperty("更新用户")
        @TableField("update_user")
    private String updateUser;

            @ApiModelProperty("更新时间")
        @TableField("update_time")
    private LocalDateTime updateTime;

            @ApiModelProperty("是否被逻辑删除 0-未被删除 1-已被删除")
        @TableField("is_delete")
    private String isDelete;


}




