package com.moirae.rosettaflow.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.moirae.rosettaflow.mapper.domain.WorkflowNodeInput;

import java.util.List;

/**
 * @author hudenian
 * @date 2021/8/18
 * @description 工作流节点输入服务接口
 */
public interface IWorkflowNodeInputService extends IService<WorkflowNodeInput> {

    /**
     * 根据工作流节点id获取工作流节点输入列表
     *
     * @param workflowNodeId 工作流节点id
     * @return 工作流节点输入列表
     */
    List<WorkflowNodeInput> queryByWorkflowNodeId(Long workflowNodeId);

    /**
     * 批量保存节点输入
     *
     * @param workflowNodeInputList 节点输入列表
     */
    void batchInsert(List<WorkflowNodeInput> workflowNodeInputList);
}
