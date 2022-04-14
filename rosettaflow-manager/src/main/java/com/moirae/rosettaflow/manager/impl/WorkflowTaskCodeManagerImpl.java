package com.moirae.rosettaflow.manager.impl;

import com.moirae.rosettaflow.common.enums.OldAndNewEnum;
import com.moirae.rosettaflow.mapper.domain.WorkflowTaskCode;
import com.moirae.rosettaflow.mapper.WorkflowTaskCodeMapper;
import com.moirae.rosettaflow.manager.WorkflowTaskCodeManager;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 工作流任务算法代码表 服务实现类
 * </p>
 *
 * @author chendai
 * @since 2022-03-30
 */
@Service
public class WorkflowTaskCodeManagerImpl extends ServiceImpl<WorkflowTaskCodeMapper, WorkflowTaskCode> implements WorkflowTaskCodeManager {

    @Override
    public Map<OldAndNewEnum, WorkflowTaskCode> copy(Long oldWorkflowTaskId, Long newWorkflowTaskId) {
        Map<OldAndNewEnum, WorkflowTaskCode> pair = new HashMap<>();
        WorkflowTaskCode old = getById(oldWorkflowTaskId);
        if(old == null){
            pair.put(OldAndNewEnum.OLD, null);
            pair.put(OldAndNewEnum.NEW, null);
            return pair;
        }
        WorkflowTaskCode newObj = new WorkflowTaskCode();
        newObj.setWorkflowTaskId(newWorkflowTaskId);
        newObj.setEditType(old.getEditType());
        newObj.setCalculateContractStruct(old.getCalculateContractStruct());
        newObj.setCalculateContractCode(old.getCalculateContractCode());
        newObj.setDataSplitContractCode(old.getDataSplitContractCode());
        save(newObj);

        pair.put(OldAndNewEnum.OLD, old);
        pair.put(OldAndNewEnum.NEW, newObj);
        return pair;
    }

    @Override
    public WorkflowTaskCode deleteByWorkflowTaskId(Long workflowTaskId) {
        WorkflowTaskCode workflowTaskCode = getById(workflowTaskId);
        if(workflowTaskCode != null){
            removeById(workflowTaskCode.getWorkflowTaskId());
        }
        return workflowTaskCode;
    }
}
