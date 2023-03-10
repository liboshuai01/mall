package com.liboshuai.mall.admin.module.cms.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.liboshuai.mall.admin.common.base.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * <p>
 * 帮助分类表
 * </p>
 *
 * @author liboshuai
 * @since 2022-09-16
 */
@Data
@Builder
@TableName("cms_help_category")
@ApiModel(value = "CmsHelpCategory对象", description = "帮助分类表")
public class CmsHelpCategory extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @TableField("name")
    private String name;

    @ApiModelProperty("分类图标")
    @TableField("icon")
    private String icon;

    @ApiModelProperty("专题数量")
    @TableField("help_count")
    private Integer helpCount;

    @TableField("show_status")
    private Integer showStatus;

    @TableField("sort")
    private Integer sort;

}




