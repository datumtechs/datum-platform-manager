package com.platon.rosettaflow.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.platon.rosettaflow.common.enums.WorkflowRunStatusEnum;
import com.platon.rosettaflow.mapper.WorkflowTempMapper;
import com.platon.rosettaflow.mapper.domain.Workflow;
import com.platon.rosettaflow.mapper.domain.WorkflowTemp;
import com.platon.rosettaflow.service.IWorkflowTempService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author hudenian
 * @date 2021/9/7
 * @description 工作流模板服务实现类
 */
@Slf4j
@Service
public class WorkflowTempServiceImpl extends ServiceImpl<WorkflowTempMapper, WorkflowTemp> implements IWorkflowTempService {
    @Override
    public void truncate() {
        this.baseMapper.truncate();
    }

    @Override
    public Long addWorkflowTemplate(long projectTemplateId, Workflow workflow) {
        WorkflowTemp workflowTemp = new WorkflowTemp();
        workflowTemp.setProjectTempId(projectTemplateId);
        workflowTemp.setWorkflowName(workflow.getWorkflowName());
        workflowTemp.setWorkflowDesc(workflow.getWorkflowDesc());
        workflowTemp.setNodeNumber(workflow.getNodeNumber());
        workflowTemp.setRunStatus(WorkflowRunStatusEnum.UN_RUN.getValue());
        this.save(workflowTemp);
        return workflowTemp.getId();
    }
}
