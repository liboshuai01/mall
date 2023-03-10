package com.liboshuai.mall.admin.module.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liboshuai.mall.admin.module.ums.domain.entity.UmsAdminRoleRelation;

import java.util.List;

/**
 * <p>
 * 后台用户和角色关系表 服务类
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
public interface UmsAdminRoleRelationService extends IService<UmsAdminRoleRelation> {
    /**
     * 根据用户id查询角色id集合
     */
    List<Long> findRoleIdsByUserId(Long userId);

}
