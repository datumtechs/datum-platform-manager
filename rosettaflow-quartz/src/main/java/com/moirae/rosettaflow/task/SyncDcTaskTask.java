package com.moirae.rosettaflow.task;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.moirae.rosettaflow.common.enums.DataSyncTypeEnum;
import com.moirae.rosettaflow.grpc.metadata.req.dto.MetaDataSummaryDto;
import com.moirae.rosettaflow.grpc.metadata.resp.dto.MetaDataDetailResponseDto;
import com.moirae.rosettaflow.grpc.service.GrpcMetaDataService;
import com.moirae.rosettaflow.grpc.service.GrpcTaskService;
import com.moirae.rosettaflow.grpc.service.TaskPowerSupplier;
import com.moirae.rosettaflow.grpc.task.req.dto.TaskDetailResponseDto;
import com.moirae.rosettaflow.grpc.task.req.dto.TaskDetailShowDto;
import com.moirae.rosettaflow.mapper.domain.*;
import com.moirae.rosettaflow.mapper.enums.MetaDataFileTypeEnum;
import com.moirae.rosettaflow.mapper.enums.MetaDataStatusEnum;
import com.moirae.rosettaflow.service.IDataSyncService;
import com.moirae.rosettaflow.service.MetaDataService;
import com.moirae.rosettaflow.service.OrganizationService;
import com.zengtengpeng.annotation.Lock;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 同步元数据定时任务, 多久同步一次待确认
 *
 * @author hudenian
 * @date 2021/8/23
 */
@Slf4j
@Component
public class SyncDcTaskTask {

    @Resource
    private GrpcTaskService grpcTaskService;

    @Resource
    private OrganizationService organizationService;

    @Resource
    private IDataSyncService dataSyncService;

    @Scheduled(fixedDelay = 5 * 1000)
    @Lock(keys = "SyncDcTaskTask")
    public void run() {
        long begin = DateUtil.current();
        try {
            List<String> identityIdList = organizationService.getEffectiveIdentityIdList();
            for (String identityId : identityIdList) {
                dataSyncService.sync(DataSyncTypeEnum.TASK.getDataType() + "-" + identityId, DataSyncTypeEnum.TASK.getDesc(),//1.根据dataType同步类型获取新的同步时间DataSync
                        (latestSynced) -> {//2.根据新的同步时间latestSynced获取分页列表grpcResponseList
                            return grpcTaskService.getTaskDetailList(organizationService.getChannel(identityId), latestSynced);
                        },
                        (grpcResponseList) -> {//3.根据分页列表grpcResponseList实现实际业务逻辑
                            batchUpdateTask(grpcResponseList);
                        },
                        (grpcResponseList) -> {//4.根据分页列表grpcResponseList获取最新的同步时间latestSynced
                            return grpcResponseList
                                    .get(grpcResponseList.size() - 1)
                                    .getInformation().getUpdateAt();
                        });
            }
        } catch (Exception e) {
            log.error("任务信息同步,从net同步任务失败,失败原因：{}", e.getMessage(), e);
        }
        log.info("任务信息同步结束，总耗时:{}ms", DateUtil.current() - begin);
    }

    private void batchUpdateTask(List<TaskDetailResponseDto> taskDetailResponseDtoList) {
        List<Task> taskList = new ArrayList<>();
        List<TaskAlgoProvider> taskAlgoProviderList = new ArrayList<>();
        List<TaskDataProvider> taskDataProviderList = new ArrayList<>();
        List<TaskMetaDataColumn> taskMetaDataColumnList = new ArrayList<>();
        List<TaskPowerProvider> taskPowerProviderList = new ArrayList<>();
        List<TaskResultConsumer> taskResultConsumerList = new ArrayList<>();
        taskDetailResponseDtoList.stream().forEach(item ->{
            TaskDetailShowDto information = item.getInformation();

            TaskAlgoProvider taskAlgoProvider = new TaskAlgoProvider();
            taskAlgoProvider.setTaskId(information.getTaskId());
            taskAlgoProvider.setIdentityId(information.getAlgoSupplier().getIdentityId());
            taskAlgoProvider.setPartyId(information.getAlgoSupplier().getPartyId());
            taskAlgoProviderList.add(taskAlgoProvider);

            information.getDataSuppliers().forEach(subItem ->{
                TaskDataProvider taskDataProvider = new TaskDataProvider();
                taskDataProvider.setTaskId(information.getTaskId());
                taskDataProvider.setMetaDataId(subItem.getMetaDataId());
                taskDataProvider.setIdentityId(subItem.getMemberInfo().getIdentityId());
                taskDataProvider.setPartyId(subItem.getMemberInfo().getPartyId());
                taskDataProviderList.add(taskDataProvider);
            });
            information.getPowerSuppliers().forEach(subItem ->{
                TaskPowerProvider taskPowerProvider = new TaskPowerProvider();
                taskPowerProvider.setTaskId(information.getTaskId());
                taskPowerProvider.setIdentityId(subItem.getMemberInfo().getIdentityId());
                taskPowerProvider.setPartyId(subItem.getMemberInfo().getPartyId());
                taskPowerProvider.setUsedCore(subItem.getResourceUsedInfo().getUsedProcessor());
                taskPowerProvider.setUsedMemory(subItem.getResourceUsedInfo().getUsedMem());
                taskPowerProvider.setUsedBandwidth(subItem.getResourceUsedInfo().getUsedBandwidth());
                taskPowerProviderList.add(taskPowerProvider);
            });
            information.getReceivers().forEach(subItem ->{
                TaskResultConsumer taskResultConsumer = new TaskResultConsumer();
                taskResultConsumer.setTaskId(information.getTaskId());
                taskResultConsumer.setConsumerIdentityId(subItem.getIdentityId());
                taskResultConsumer.setConsumerPartyId(subItem.getPartyId());
                taskResultConsumerList.add(taskResultConsumer);
            });
            Task task = new Task();
            task.setId(information.getTaskId());
            task.setTaskName(information.getTaskName());
            task.setUserId(information.getUser());
            task.setUserType(information.getUserType());
            task.setRequiredMemory(information.getOperationCost().getMemory());
            task.setRequiredCore(information.getOperationCost().getProcessor());
            task.setRequiredBandwidth(information.getOperationCost().getBandwidth());
            task.setRequiredDuration(information.getOperationCost().getDuration());
            task.setOwnerIdentityId(information.getSender().getIdentityId());
            task.setOwnerPartyId(information.getSender().getPartyId());
            task.setCreateAt(new Date(information.getCreateAt()));
            task.setStartAt(new Date(information.getStartAt()));
            task.setEndAt(information.getEndAt() == null || information.getEndAt() == 0 ? null : new Date(information.getEndAt()));
            task.setStatus(information.getState());
            taskList.add(task);
        });


    }
}
