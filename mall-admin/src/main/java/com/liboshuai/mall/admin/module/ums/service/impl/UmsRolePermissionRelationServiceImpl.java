package com.liboshuai.mall.admin.module.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liboshuai.mall.admin.module.ums.domain.entity.UmsRolePermissionRelation;
import com.liboshuai.mall.admin.module.ums.mapper.UmsRolePermissionRelationMapper;
import com.liboshuai.mall.admin.module.ums.service.UmsRolePermissionRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 后台用户角色和权限关系表 服务实现类
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Service
public class UmsRolePermissionRelationServiceImpl extends ServiceImpl<UmsRolePermissionRelationMapper, UmsRolePermissionRelation> implements UmsRolePermissionRelationService {
    @Autowired
    private UmsRolePermissionRelationMapper umsRolePermissionRelationMapper;

    /**
     * 根据角色id集合查询权限id集合
     */
    @Override
    public List<Long> findPermissionIdsByRoleIds(List<Long> roleIds) {
        LambdaQueryWrapper<UmsRolePermissionRelation> umsRolePermissionRelationLambdaQueryWrapper = new LambdaQueryWrapper<>();
        umsRolePermissionRelationLambdaQueryWrapper.in(UmsRolePermissionRelation::getRoleId, roleIds);
        List<UmsRolePermissionRelation> umsRolePermissionRelations = umsRolePermissionRelationMapper.selectList(umsRolePermissionRelationLambdaQueryWrapper);
        return umsRolePermissionRelations.stream().map(UmsRolePermissionRelation::getPermissionId).collect(Collectors.toList());
    }
}
