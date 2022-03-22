package com.moirae.rosettaflow.controller;


import com.moirae.rosettaflow.req.org.DelIpPortBindReq;
import com.moirae.rosettaflow.req.org.GetOrgListReq;
import com.moirae.rosettaflow.req.org.IpPortBindReq;
import com.moirae.rosettaflow.service.OrganizationService;
import com.moirae.rosettaflow.utils.ConvertUtils;
import com.moirae.rosettaflow.vo.PageVo;
import com.moirae.rosettaflow.vo.ResponseVo;
import com.moirae.rosettaflow.vo.org.OrgDetailsVo;
import com.moirae.rosettaflow.vo.org.OrgStatsVo;
import com.moirae.rosettaflow.vo.org.OrgVo;
import com.moirae.rosettaflow.vo.org.UserOrgVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hudenian
 * @date 2021/12/15
 */
@Slf4j
@RestController
@Api(tags = "组织管理关接口")
@RequestMapping(value = "org", produces = MediaType.APPLICATION_JSON_VALUE)
public class OrgController {

    @Resource
    private OrganizationService organizationService;

    @GetMapping("getOrgStats")
    @ApiOperation(value = "查询组织统计", notes = "查询组织统计")
    public ResponseVo<OrgStatsVo> getOrgStats() {
        return ResponseVo.createSuccess(new OrgStatsVo());
    }

    @GetMapping("getOrgList")
    @ApiOperation(value = "查询组织列表", notes = "查询组织列表")
    public ResponseVo<PageVo<OrgVo>> getOrgList(@Valid GetOrgListReq req) {
        List<OrgVo> orgTaskVoList = new ArrayList<>();
        return ResponseVo.createSuccess(ConvertUtils.convertPageVo(null, orgTaskVoList));
    }

    @GetMapping("getOrgDetails")
    @ApiOperation(value = "查询组织详情", notes = "查询组织详情")
    public ResponseVo<OrgDetailsVo> getTaskDetails(@RequestParam String identityId) {
        return ResponseVo.createSuccess(new OrgDetailsVo());
    }

    @GetMapping("getUserOrgList")
    @ApiOperation(value = "查询用户可用的组织列表", notes = "查询用户可用的组织列表")
    public ResponseVo<PageVo<UserOrgVo>> getUserOrgList() {
        List<UserOrgVo> orgTaskVoList = new ArrayList<>();
        return ResponseVo.createSuccess(ConvertUtils.convertPageVo(null, orgTaskVoList));
    }

    @PostMapping("joinOrg")
    @ApiOperation(value = "用户加入组织", notes = "用户加入组织")
    public ResponseVo<?> joinOrg(@RequestBody @Valid IpPortBindReq ipPortBindReq) {
        organizationService.addOrganizationByUser(ipPortBindReq.getIdentityIp(), ipPortBindReq.getIdentityPort());
        return ResponseVo.createSuccess();
    }

    @PostMapping("quitOrg")
    @ApiOperation(value = "删除用户绑定的组织", notes = "删除用户绑定的组织")
    public ResponseVo<?> quitOrg(@RequestBody @Valid DelIpPortBindReq delIpPortBindReq) {
        organizationService.deleteOrganizationByUser(delIpPortBindReq.getIdentityId());
        return ResponseVo.createSuccess();
    }
}
