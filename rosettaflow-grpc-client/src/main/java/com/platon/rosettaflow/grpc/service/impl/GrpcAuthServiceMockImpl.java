package com.platon.rosettaflow.grpc.service.impl;

import com.platon.rosettaflow.common.enums.MetaDataUsageEnum;
import com.platon.rosettaflow.common.enums.UserMetaDataAuditEnum;
import com.platon.rosettaflow.common.enums.UserTypeEnum;
import com.platon.rosettaflow.grpc.client.MetaDataServiceClient;
import com.platon.rosettaflow.grpc.constant.GrpcConstant;
import com.platon.rosettaflow.grpc.identity.dto.NodeIdentityDto;
import com.platon.rosettaflow.grpc.metadata.req.dto.ApplyMetaDataAuthorityRequestDto;
import com.platon.rosettaflow.grpc.metadata.req.dto.MetaDataAuthorityDto;
import com.platon.rosettaflow.grpc.metadata.req.dto.MetaDataUsageRuleDto;
import com.platon.rosettaflow.grpc.metadata.resp.dto.ApplyMetaDataAuthorityResponseDto;
import com.platon.rosettaflow.grpc.metadata.resp.dto.GetMetaDataAuthorityDto;
import com.platon.rosettaflow.grpc.metadata.resp.dto.MetadataUsedQuoDto;
import com.platon.rosettaflow.grpc.service.GrpcAuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author hudenian
 * @date 2021/8/24
 * @description 获取元数据mock信息
 */
@Slf4j
@Service
@Profile({"dev", "local"})
public class GrpcAuthServiceMockImpl implements GrpcAuthService {

    @Resource
    private MetaDataServiceClient metaDataServiceClient;

    @Override
    public ApplyMetaDataAuthorityResponseDto applyMetaDataAuthority(ApplyMetaDataAuthorityRequestDto requestDto) {
        ApplyMetaDataAuthorityResponseDto applyMetaDataAuthorityResponseDto = new ApplyMetaDataAuthorityResponseDto();
        applyMetaDataAuthorityResponseDto.setStatus(GrpcConstant.GRPC_SUCCESS_CODE);
        applyMetaDataAuthorityResponseDto.setMsg(GrpcConstant.OK);
        applyMetaDataAuthorityResponseDto.setMetaDataAuthId("testAuthId");
        return applyMetaDataAuthorityResponseDto;
    }

    @Override
    public List<GetMetaDataAuthorityDto> getMetaDataAuthorityList() {
        List<GetMetaDataAuthorityDto> metaDataAuthorityDtoList = new ArrayList<>();

        GetMetaDataAuthorityDto getMetaDataAuthorityDto;
        MetaDataAuthorityDto metaDataAuthorityDto;
        NodeIdentityDto nodeIdentityDto;
        MetaDataUsageRuleDto metaDataUsageDto;

        for (int i = 0; i < 10; i++) {
            getMetaDataAuthorityDto = new GetMetaDataAuthorityDto();
            getMetaDataAuthorityDto.setMetaDataAuthId("MetaDataId" + i);
            getMetaDataAuthorityDto.setUser("0x93c1e3b0e82fcb50d9c4b4568b3d892539668a20");
            getMetaDataAuthorityDto.setUserType(UserTypeEnum.ALAYA.getValue());

            metaDataAuthorityDto = new MetaDataAuthorityDto();
            nodeIdentityDto = new NodeIdentityDto();
            nodeIdentityDto.setNodeName("节点" + i + "名字");
            nodeIdentityDto.setNodeId("节点" + i + "的Id");
            nodeIdentityDto.setIdentityId("节点" + i + "的identityId");
            nodeIdentityDto.setIdentityId("节点" + i + "的identityId");
            metaDataAuthorityDto.setOwner(nodeIdentityDto);

            metaDataAuthorityDto.setMetaDataId("metaDataId" + i);

            metaDataUsageDto = new MetaDataUsageRuleDto();
            metaDataUsageDto.setUseType(MetaDataUsageEnum.TIMES.getValue());
            metaDataUsageDto.setStartAt(1629877270100L);
            metaDataUsageDto.setEndAt(1629877270100L);
            metaDataUsageDto.setTimes(100 + i);
            metaDataAuthorityDto.setMetaDataUsageDto(metaDataUsageDto);
            getMetaDataAuthorityDto.setMetaDataAuthorityDto(metaDataAuthorityDto);
            if (i == 0) {
                getMetaDataAuthorityDto.setAuditMetaDataOption((int) UserMetaDataAuditEnum.AUDIT_PASSED.getValue());
            } else if (i == 1) {
                getMetaDataAuthorityDto.setAuditMetaDataOption((int) UserMetaDataAuditEnum.AUDIT_REFUSED.getValue());
            } else if (i == 2) {
                getMetaDataAuthorityDto.setAuditMetaDataOption((int) UserMetaDataAuditEnum.AUDIT_PENDING.getValue());
            } else {
                getMetaDataAuthorityDto.setAuditMetaDataOption((int) UserMetaDataAuditEnum.AUDIT_UNKNOWN.getValue());
            }

            MetadataUsedQuoDto metadataUsedQuoDto = new MetadataUsedQuoDto();
            metadataUsedQuoDto.setMetadataUsageType(2);
            metadataUsedQuoDto.setExpire(false);
            metadataUsedQuoDto.setUsedTimes(100 + i);
            //对应数据授权信息中元数据的使用实况
            getMetaDataAuthorityDto.setMetadataUsedQuoDto(metadataUsedQuoDto);
            getMetaDataAuthorityDto.setApplyAt(1629877270100L);
            getMetaDataAuthorityDto.setAuditAt(1629877270100L);
            metaDataAuthorityDtoList.add(getMetaDataAuthorityDto);
        }
        return metaDataAuthorityDtoList;
    }

    @Override
    public NodeIdentityDto getNodeIdentity() {
        NodeIdentityDto nodeIdentityDto = new NodeIdentityDto();
        nodeIdentityDto.setNodeName("mockNodeName");
        nodeIdentityDto.setNodeId("mockNodeId");
        nodeIdentityDto.setIdentityId("mockIdentityId");
        nodeIdentityDto.setStatus(1);
        return nodeIdentityDto;
    }

    @Override
    public List<NodeIdentityDto> getIdentityList() {
        return null;
    }
}
