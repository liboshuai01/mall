package com.liboshuai.mall.admin.module.pms.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liboshuai.mall.admin.module.pms.domain.entity.PmsProductFullReduction;
import com.liboshuai.mall.admin.module.pms.mapper.PmsProductFullReductionMapper;
import com.liboshuai.mall.admin.module.pms.service.PmsProductFullReductionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品满减表(只针对同商品) 服务实现类
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Service
public class PmsProductFullReductionServiceImpl extends ServiceImpl<PmsProductFullReductionMapper, PmsProductFullReduction> implements PmsProductFullReductionService {

}
