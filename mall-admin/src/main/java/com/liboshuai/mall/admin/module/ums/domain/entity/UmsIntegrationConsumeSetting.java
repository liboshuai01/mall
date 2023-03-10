package com.liboshuai.mall.admin.module.ums.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liboshuai.mall.admin.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>
 * 积分消费设置
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Data
@TableName("ums_integration_consume_setting")
@ApiModel(value = "UmsIntegrationConsumeSetting对象", description = "积分消费设置")
public class UmsIntegrationConsumeSetting extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("每一元需要抵扣的积分数量")
    @TableField("deduction_per_amount")
    private Integer deductionPerAmount;

    @ApiModelProperty("每笔订单最高抵用百分比")
    @TableField("max_percent_per_order")
    private Integer maxPercentPerOrder;

    @ApiModelProperty("每次使用积分最小单位100")
    @TableField("use_unit")
    private Integer useUnit;

    @ApiModelProperty("是否可以和优惠券同用；0->不可以；1->可以")
    @TableField("coupon_status")
    private Integer couponStatus;

}




