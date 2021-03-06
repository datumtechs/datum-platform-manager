package com.datum.platform.mapper.domain;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 项目工作流节点输出表
 * </p>
 *
 * @author chendai
 * @since 2022-03-28
 */
@Data
@TableName("mo_workflow_task_output")
public class WorkflowTaskOutput implements Serializable {


    /**
     * 工作流任务配置id
     */
    private Long workflowTaskId;

    /**
     * 协同方组织的身份标识Id
     */
    private String identityId;

    /**
     * 存储形式: 1-明文，2:密文
     */
    @TableField("store_pattern")
    private Integer storePattern;

    /**
     * 输出内容
     */
    @TableField("output_content")
    private String outputContent;

    /**
     * 任务里面定义的 (q0 -> qN 方 ...)
     */
    @TableField("party_id")
    private String partyId;

    /**
     * 用于排序的字段
     */
    private Integer sortKey;


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
    @TableField(exist = false)
    private Org org;

}
