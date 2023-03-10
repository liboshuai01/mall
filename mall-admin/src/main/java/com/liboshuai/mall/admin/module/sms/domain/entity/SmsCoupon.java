package com.liboshuai.mall.admin.module.sms.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liboshuai.mall.admin.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 优惠卷表
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Data
@TableName("sms_coupon")
@ApiModel(value = "SmsCoupon对象", description = "优惠卷表")
public class SmsCoupon extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("优惠卷类型；0->全场赠券；1->会员赠券；2->购物赠券；3->注册赠券")
    @TableField("type")
    private Integer type;

    @TableField("name")
    private String name;

    @ApiModelProperty("使用平台：0->全部；1->移动；2->PC")
    @TableField("platform")
    private Integer platform;

    @ApiModelProperty("数量")
    @TableField("count")
    private Integer count;

    @ApiModelProperty("金额")
    @TableField("amount")
    private BigDecimal amount;

    @ApiModelProperty("每人限领张数")
    @TableField("per_limit")
    private Integer perLimit;

    @ApiModelProperty("使用门槛；0表示无门槛")
    @TableField("min_point")
    private BigDecimal minPoint;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @ApiModelProperty("使用类型：0->全场通用；1->指定分类；2->指定商品")
    @TableField("use_type")
    private Integer useType;

    @ApiModelProperty("备注")
    @TableField("note")
    private String note;

    @ApiModelProperty("发行数量")
    @TableField("publish_count")
    private Integer publishCount;

    @ApiModelProperty("已使用数量")
    @TableField("use_count")
    private Integer useCount;

    @ApiModelProperty("领取数量")
    @TableField("receive_count")
    private Integer receiveCount;

    @ApiModelProperty("可以领取的日期")
    @TableField("enable_time")
    private LocalDateTime enableTime;

    @ApiModelProperty("优惠码")
    @TableField("code")
    private String code;

    @ApiModelProperty("可领取的会员类型：0->无限时")
    @TableField("member_level")
    private Integer memberLevel;

}




