package com.datum.platform.mapper.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 工作流任务变量表
 * </p>
 *
 * @author chendai
 * @since 2022-03-28
 */
@Data
@TableName("mo_workflow_task_variable")
public class WorkflowTaskVariable implements Serializable {


    /**
     * 工作流任务配置id
     */
    private Long workflowTaskId;

    /**
     * 变量key
     */
    private String varKey;

    /**
     * 变量值
     */
    @TableField("var_value")
    private String varValue;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(update = "now()")
    private Date updateTime;

    private static final long serialVersionUID = 1L;

}
