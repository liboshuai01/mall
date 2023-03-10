package com.liboshuai.mall.admin.module.pms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liboshuai.mall.admin.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 商品评价表
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Data
@TableName("pms_comment")
@ApiModel(value = "PmsComment对象", description = "商品评价表")
public class PmsComment extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("product_id")
    private Long productId;

    @TableField("member_nick_name")
    private String memberNickName;

    @TableField("product_name")
    private String productName;

    @ApiModelProperty("评价星数：0->5")
    @TableField("star")
    private Integer star;

    @ApiModelProperty("评价的ip")
    @TableField("member_ip")
    private String memberIp;

    @TableField("show_status")
    private Integer showStatus;

    @ApiModelProperty("购买时的商品属性")
    @TableField("product_attribute")
    private String productAttribute;

    @TableField("collect_couont")
    private Integer collectCouont;

    @TableField("read_count")
    private Integer readCount;

    @TableField("content")
    private String content;

    @ApiModelProperty("上传图片地址，以逗号隔开")
    @TableField("pics")
    private String pics;

    @ApiModelProperty("评论用户头像")
    @TableField("member_icon")
    private String memberIcon;

    @TableField("replay_count")
    private Integer replayCount;

}




