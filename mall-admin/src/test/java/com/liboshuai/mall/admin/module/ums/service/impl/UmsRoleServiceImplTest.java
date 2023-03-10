package com.liboshuai.mall.admin.module.ums.service.impl;

import com.liboshuai.mall.admin.MallAdminApplication;
import com.liboshuai.mall.admin.module.ums.domain.dto.UmsRoleDTO;
import com.liboshuai.mall.admin.module.ums.service.UmsAdminRoleRelationService;
import com.liboshuai.mall.admin.module.ums.service.UmsAdminService;
import com.liboshuai.mall.admin.module.ums.service.UmsRoleService;
import junit.framework.TestCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MallAdminApplication.class)
public class UmsRoleServiceImplTest extends TestCase {

    @Autowired
    private UmsAdminService umsAdminService;

    @Autowired
    private UmsAdminRoleRelationService umsAdminRoleRelationService;

    @Autowired
    private UmsRoleService umsRoleService;

    private static final String USERNAME = "admin";

    @Test
    public void testFindRolesByRoleIds() {
        Long adminId = umsAdminService.findUserIdByUserName(USERNAME);
        List<Long> roleIds = umsAdminRoleRelationService.findRoleIdsByUserId(adminId);
        List<UmsRoleDTO> roles = umsRoleService.findRolesByRoleIds(roleIds);
    }

    @Test
    public void testFindRolesByUsername() {
        List<UmsRoleDTO> roles = umsRoleService.findRolesByUsername(USERNAME);
    }
}