package com.liboshuai.mall.tiny.module.ums.domain.dao;

    import com.baomidou.mybatisplus.annotation.IdType;
    import com.baomidou.mybatisplus.annotation.TableField;
    import com.baomidou.mybatisplus.annotation.TableId;
    import com.baomidou.mybatisplus.annotation.TableName;
    import java.io.Serializable;
    import java.time.LocalDateTime;
    import io.swagger.annotations.ApiModel;
    import io.swagger.annotations.ApiModelProperty;
    import lombok.Data;

/**
* <p>
    * 后台用户登录日志表
    * </p>
*
* @author liboshuai
* @since 2022-09-16
*/
    @Data
    @TableName("ums_admin_login_log")
    @ApiModel(value = "UmsAdminLoginLog对象", description = "后台用户登录日志表")
    public class UmsAdminLoginLog implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

        @TableField("admin_id")
    private Long adminId;

        @TableField("ip")
    private String ip;

        @TableField("address")
    private String address;

            @ApiModelProperty("浏览器登录类型")
        @TableField("user_agent")
    private String userAgent;

            @ApiModelProperty("创建用户")
        @TableField("create_user")
    private String createUser;

            @ApiModelProperty("创建时间")
        @TableField("create_time")
    private LocalDateTime createTime;

            @ApiModelProperty("更新用户")
        @TableField("update_user")
    private String updateUser;

            @ApiModelProperty("更新时间")
        @TableField("update_time")
    private LocalDateTime updateTime;

            @ApiModelProperty("是否被逻辑删除 0-未被删除 1-已被删除")
        @TableField("is_delete")
    private String isDelete;


}




