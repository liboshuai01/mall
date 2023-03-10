package com.liboshuai.mall.admin.module.sms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liboshuai.mall.admin.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 优惠券使用、领取历史表
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Data
@TableName("sms_coupon_history")
@ApiModel(value = "SmsCouponHistory对象", description = "优惠券使用、领取历史表")
public class SmsCouponHistory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("coupon_id")
    private Long couponId;

    @TableField("member_id")
    private Long memberId;

    @TableField("coupon_code")
    private String couponCode;

    @ApiModelProperty("领取人昵称")
    @TableField("member_nickname")
    private String memberNickname;

    @ApiModelProperty("获取类型：0->后台赠送；1->主动获取")
    @TableField("get_type")
    private Integer getType;

    @ApiModelProperty("使用状态：0->未使用；1->已使用；2->已过期")
    @TableField("use_status")
    private Integer useStatus;

    @ApiModelProperty("使用时间")
    @TableField("use_time")
    private LocalDateTime useTime;

    @ApiModelProperty("订单编号")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty("订单号码")
    @TableField("order_sn")
    private String orderSn;
}




