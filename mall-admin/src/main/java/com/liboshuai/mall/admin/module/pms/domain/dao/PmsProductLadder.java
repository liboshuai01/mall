package com.liboshuai.mall.admin.module.pms.domain.dao;

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
    * 产品阶梯价格表(只针对同商品)
    * </p>
*
* @author liboshuai
* @since 2022-12-27
*/
    @Data
    @TableName("pms_product_ladder")
    @ApiModel(value = "PmsProductLadder对象", description = "产品阶梯价格表(只针对同商品)")
    public class PmsProductLadder implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        @TableField("product_id")
    private Long productId;

            @ApiModelProperty("满足的商品数量")
        @TableField("count")
    private Integer count;

            @ApiModelProperty("折扣")
        @TableField("discount")
    private BigDecimal discount;

            @ApiModelProperty("折后价格")
        @TableField("price")
    private BigDecimal price;

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

            @ApiModelProperty("逻辑删除: 0-未删除, 1-已删除")
        @TableField("is_delete")
    private String isDelete;


}




