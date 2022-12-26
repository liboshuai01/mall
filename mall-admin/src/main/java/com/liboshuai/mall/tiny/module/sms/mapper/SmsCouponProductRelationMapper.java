package com.liboshuai.mall.tiny.module.sms.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liboshuai.mall.tiny.module.sms.domain.entity.SmsCouponProductRelation;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 优惠券和产品的关系表 Mapper 接口
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Mapper
public interface SmsCouponProductRelationMapper extends BaseMapper<SmsCouponProductRelation> {

}