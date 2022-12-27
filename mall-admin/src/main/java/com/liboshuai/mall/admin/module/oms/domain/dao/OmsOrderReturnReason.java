package com.liboshuai.mall.admin.module.oms.domain.dao;

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
    * 退货原因表
    * </p>
*
* @author liboshuai
* @since 2022-12-27
*/
    @Data
    @TableName("oms_order_return_reason")
    @ApiModel(value = "OmsOrderReturnReason对象", description = "退货原因表")
    public class OmsOrderReturnReason implements Serializable {

    private static final long serialVersionUID = 1L;

            @TableId(value = "id", type = IdType.AUTO)
    private Long id;

            @ApiModelProperty("退货类型")
        @TableField("name")
    private String name;

        @TableField("sort")
    private Integer sort;

            @ApiModelProperty("状态：0->不启用；1->启用")
        @TableField("status")
    private Integer status;

            @ApiModelProperty("创建时间")
        @TableField("create_time")
    private LocalDateTime createTime;

            @ApiModelProperty("添加时间")
        @TableField("add_time")
    private LocalDateTime addTime;

            @ApiModelProperty("创建用户")
        @TableField("create_user")
    private String createUser;

            @ApiModelProperty("更新用户")
        @TableField("update_user")
    private String updateUser;

            @ApiModelProperty("更新时间")
        @TableField("update_time")
    private LocalDateTime updateTime;

            @ApiModelProperty("逻辑删除: 0-未删除, 1-已删除")
        @TableField("is_delete")
    private String isDelete;


}




