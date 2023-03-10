package com.liboshuai.mall.admin.module.ums.domain.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @Author: liboshuai
 * @Date: 2022-09-10 14:56
 * @Description: 用户注册登录退出vo
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UmsAdminVO implements Serializable {
    private static final long serialVersionUID = 1321190682152399912L;

    @ApiModelProperty("id")
    private Long id;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("头像")
    private String icon;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("备注信息")
    private String note;

}
