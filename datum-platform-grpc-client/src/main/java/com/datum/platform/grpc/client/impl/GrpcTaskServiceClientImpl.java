package com.datum.platform.grpc.client.impl;

import carrier.api.TaskRpcApi;
import carrier.api.TaskServiceGrpc;
import carrier.types.Common;
import carrier.types.Taskdata;
import com.datum.platform.common.enums.ErrorMsg;
import com.datum.platform.common.enums.RespCodeEnum;
import com.datum.platform.common.exception.BusinessException;
import com.datum.platform.grpc.client.GrpcTaskServiceClient;
import com.datum.platform.grpc.constant.GrpcConstant;
import io.grpc.Channel;
import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author admin
 * @date 2021/9/1
 * @description 任务处理相关接口
 */
@Slf4j
@Component
public class GrpcTaskServiceClientImpl implements GrpcTaskServiceClient {

    @GrpcClient("carrier-grpc-server")
    TaskServiceGrpc.TaskServiceBlockingStub taskServiceBlockingStub;

    @GrpcClient("carrier-grpc-server")
    TaskServiceGrpc.TaskServiceStub taskServiceStub;

    @Override
    public List<Taskdata.TaskDetail> getGlobalTaskDetailList(Long latestSynced) {
        TaskRpcApi.GetTaskDetailListRequest request = TaskRpcApi.GetTaskDetailListRequest.newBuilder()
                .setLastUpdated(latestSynced)
                .setPageSize(GrpcConstant.PAGE_SIZE)
                .build();
        TaskRpcApi.GetTaskDetailListResponse response = taskServiceBlockingStub.getGlobalTaskDetailList(request);

        if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            log.error("GrpcTaskServiceClientImpl->getTaskDetailList() fail reason:{}", response.getMsg());
            throw new BusinessException(response.getStatus(), response.getMsg());
        }
        return response.getTasksList();
    }

    @Override
    public List<Taskdata.TaskEvent> getTaskEventList(String taskId) {
        TaskRpcApi.GetTaskEventListRequest getTaskEventListRequest = TaskRpcApi.GetTaskEventListRequest.newBuilder().setTaskId(taskId).build();
        TaskRpcApi.GetTaskEventListResponse response = taskServiceBlockingStub.getTaskEventList(getTaskEventListRequest);

        if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            log.error("GrpcTaskServiceClientImpl->getTaskDetailList() fail reason:{}", response.getMsg());
            throw new BusinessException(response.getStatus(), response.getMsg());
        }
        return response.getTaskEventsList();
    }

    @Override
    public TaskRpcApi.PublishTaskDeclareResponse publishTaskDeclare(Channel channel, TaskRpcApi.PublishTaskDeclareRequest request) {
        log.info("publishTaskDeclare req = {}", request);
        TaskRpcApi.PublishTaskDeclareResponse response = TaskServiceGrpc.newBlockingStub(channel).publishTaskDeclare(request);
        log.info("publishTaskDeclare resp = {}", response);
        return response;
    }

    @Override
    public Common.SimpleResponse terminateTask(Channel channel, TaskRpcApi.TerminateTaskRequest request) {
        return TaskServiceGrpc.newBlockingStub(channel).terminateTask(request);
    }

    @Override
    public TaskRpcApi.EstimateTaskGasResponse estimateTaskGas(Channel channel, TaskRpcApi.EstimateTaskGasRequest request) {
        TaskRpcApi.EstimateTaskGasResponse response = TaskServiceGrpc.newBlockingStub(channel).estimateTaskGas(request);
        if (response.getStatus() != GrpcConstant.GRPC_SUCCESS_CODE) {
            log.error("GrpcTaskServiceClientImpl->estimateTaskGas() fail reason: code={} msg={}",response.getStatus(), response.getMsg());
            throw new BusinessException(RespCodeEnum.BIZ_FAILED, ErrorMsg.WORKFLOW_FEE_ESTIMATE_FAIL.getMsg());
        }
        return response;
    }
}
