package com.liboshuai.mall.admin.module.ums.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liboshuai.mall.admin.module.ums.domain.entity.UmsRole;
import com.liboshuai.mall.admin.module.ums.domain.dto.UmsRoleDTO;

import java.util.List;

/**
 * <p>
 * 后台用户角色表 服务类
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
public interface UmsRoleService extends IService<UmsRole> {
    /**
     * 根据角色id集合查询角色信息
     */
    List<UmsRoleDTO> findRolesByRoleIds(List<Long> roleIds);

    /**
     * 根据用户名获取角色信息集合
     */
    List<UmsRoleDTO> findRolesByUsername(String username);
}
