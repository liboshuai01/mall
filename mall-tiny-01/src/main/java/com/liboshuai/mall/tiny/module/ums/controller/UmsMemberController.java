package com.liboshuai.mall.tiny.module.ums.controller;


import com.liboshuai.mall.tiny.common.api.ResponseResult;
import com.liboshuai.mall.tiny.module.ums.service.UmsMemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author liboshuai
 * @since 2022-07-26
 */
@Api(tags = "会员登录注册管理", value = "UmsMemberController")
@RestController
@RequestMapping("/mall.tiny/ums-member")
@Slf4j
public class UmsMemberController {

    @Autowired
    private UmsMemberService umsMemberService;

    @RequestMapping("/findUserIdByUserName")
    public ResponseResult<Long> findUserIdByUserName(String userName) {
        Long userId = umsMemberService.findUserIdByUserName(userName);
        return ResponseResult.success(userId);
    }

}
