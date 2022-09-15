package com.liboshuai.mall.tiny.module.ums.mapper;

import com.liboshuai.mall.tiny.module.ums.domain.dao.UmsAdminPermissionRelation;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 后台用户和权限关系表(除角色中定义的权限以外的加减权限) Mapper 接口
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Mapper
public interface UmsAdminPermissionRelationMapper extends BaseMapper<UmsAdminPermissionRelation> {

}
