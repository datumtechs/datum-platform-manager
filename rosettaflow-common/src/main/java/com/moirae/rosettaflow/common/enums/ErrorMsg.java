package com.moirae.rosettaflow.common.enums;

import com.moirae.rosettaflow.common.constants.SysConstant;
import com.moirae.rosettaflow.common.utils.LanguageContext;

import java.util.Objects;

/**
 * @author hudenian
 * @date
 * @description
 */
public enum ErrorMsg {

    /**
     * 通用
     */
    SUCCESS("成功", "Success"),
    PARAM_ERROR("请求参数错误", "Request param error"),
    EXCEPTION("系统异常，请联系管理台", "System exception,please contact the administrator"),
    PARAM_TYPE_ERROR("参数类型错误", "Param type error"),
    REQUEST_METHOD_ERROR("请求方式错误", "Request method error"),
    PARAM_FORMAT_ERROR("参数格式错误", "Param format error"),
    BIZ_FAILED("业务失败", "Business failed"),
    BIZ_EXCEPTION("业务异常", "Business exception"),
    BIZ_QUERY_NOT_EXIST("查询内容不存在", "Query content does not exist"),

    /**
     * 用户
     */
    USER_NONCE_INVALID("nonce无效", "Nonce invalid"),
    USER_SIGN_ERROR("签名错误", "Signature error"),
    USER_UN_LOGIN("用户未登录", "User not logged in"),
    USER_ADDRESS_ERROR("用户地址有误", "Wrong user address"),
    MODIFY_USER_NAME_FAILED("昵称修改失败", "Nickname modification failed"),
    USER_NAME_EXISTED("昵称已存在", "Nickname already exists"),
    UN_LOGIN("用户未登录", "User not login"),
    TOKEN_INVALID("Token 无效", "Token invalid"),
    USER_NOT_EXIST("用户不存在", "User not exist"),
    NONCE_INVALID("Nonce 无效", "Nonce invalid"),

    /**
     * 项目
     */
    ADD_PROJ_ERROR("新增项目错误", "New project error"),
    PROJECT_NAME_EXISTED("项目名称已存在", "Project name already exists"),
    PROJECT_NOT_EXIST("项目不存在", "Project does not exist"),
    MEMBER_ROLE_EXISTED("成员角色已存在", "Member role already exists"),
    UPDATE_PROJ_ERROR("修改项目错误", "Modify project error"),
    QUERY_PROJ_LIST_ERROR("查询项目列表错误", "Query project list error"),
    QUERY_PROJ_DETAILS_ERROR("查询项目详情错误", "Query project details error"),
    ADD_PROJECT_TEMPLATE_ERROR("新增项目模板错误", "Add project template error"),
    PROJECT_TEMPLATE_NAME_EXISTED("项目模板名称已存在", "Project template name already exists"),
    ALGORITHM_CODE_NOT_NOT_EXISTS("项目模板对应的工作流节点代码不存在", "Project template algorithm code not exists"),
    USER_ADMIN_PERMISSION_ERROR("您不是当前项目管理员，无操作权限！", "You are not this project administrator, No operation authority"),
    USER_NOT_PERMISSION_ERROR("您是项目查看者，暂无编辑权限！", "You are a project viewer and have no editing rights"),
    USER_ADMIN_UPDATE_ERROR("不能更改最后一个管理员权限！", "Cannot change the last admin permissions"),
    USER_ADMIN_DELETE_ERROR("不能将自己的项目权限删除！", "Cannot delete own project permissions!"),
    USER_ACCESS_PERMISSION_ERROR("您无权访问当前项目！", "You don't have permission to access the current project!"),

    /**
     * 算法
     */
    ADD_ALG_ERROR("新增算法错误", "New algorithm error"),
    UPDATE_ALG_ERROR("修改算法错误", "Modify algorithm error"),
    ALG_NAME_EXISTED("算法名称已存在", "Algorithm name already exists"),
    QUERY_ALG_DETAILS_ERROR("查询算法详情错误", "Query algorithm details error"),
    ALG_NOT_EXIST("算法不存在", "Algorithm does not exist"),
    ALG_CODE_NOT_EXIST("算法代码不存在", "Algorithm code does not exist"),
    ALGORITHM_AUTH_NOT_EXIST("用户算法授权信息不存在或者授权已失效", "Algorithm auth not exist or invalidation"),
    ALG_TYPE_NOT_EXIST("算法大类不存在", "Algorithm type does not exist"),
    ALG_VARIABLE_STRUCT_ERROR("算法变量可变参数json结构错误", "Algorithm variable variable parameter json structure error"),
    ALG_REPEAT_ERROR("一个工作流中，不能存在重复的算法！", "There can be no duplicate algorithms in a workflow"),

    /**
     * 数据
     */
    APPLY_METADATA_USAGE_TYPE_ERROR("元数据使用方式输入格式错误", "Apply metadata usage type error"),
    METADATA_NOT_EXIST("元数据不存在", "Meta data not exist"),
    METADATA_UNAVAILABLE("元数据不可用", "Meta data unavailable"),
    METADATA_UNAVAILABLE_FORMAT("元数据不可用 {}", "Meta data unavailable {}"),
    METADATA_AUTH_TIMES_ERROR("元数据授权申请按次时，使用次数必须大于零", "Metadata authorization by times, the times must be greater than zero"),
    METADATA_AUTH_TIME_ERROR("元数据授权申请时间错误", "Metadata authorization apply time error"),
    METADATA_AUTH_SAVE_ERROR("元数据授权申请保存失败", "Metadata authorization apply save error"),
    METADATA_AUTH_UNEXPIRED_ERROR("元数据申请审核通过未过期，不能重新发起申请", "Meta data auth unexpired,can not reapply"),
    METADATA_AUTH_PENDING_ERROR("元数据申请等待审核中，不能重新发起申请", "Meta data auth audit pending,can not reapply"),
    METADATA_USER_NOT_EXIST("用户授权数据不存在", "User meta data not exist"),
    METADATA_USER_DATA_EXPIRE("有授权数据已过期，请检查", "Authorized data has expired, please check"),
    METADATA_RESULT_DOWNLOAD_FAIL("元数据结果下载失败", "Metadata result download fail"),
    METADATA_RESULT_DOWNLOAD_ERROR("元数据结果下载异常", "Metadata result download error"),
    METADATA_USER_AUTH_METADATA_STATE_UPDATE_FAIL("用户元数据授权状态更新失败", "Metadata user auth metadata state update fail"),
    METADATA_USER_AUTH_METADATA_REVOKE_ERROR("用户元数据授权状态错误，不能发起撤销", "Metadata user auth metadata state error,can not revoke"),
    METADATA_USER_AUTH_METADATA_RPC_ERROR("申请授权调度服务网络异常！", "Application for authorization dispatch service network is abnormal!"),


    /**
     * 工作流
     */
    WORKFLOW_EXECUTE_VALUE_INSUFFICIENT("工作流执行时账户余额不足", "Insufficient account balance during workflow execution"),
    WORKFLOW_BEEN_RUN("工作流存在运行信息", "Workflow has running information"),
    WORKFLOW_NOT_EXIST("工作流不存在", "Workflow does not exist"),
    WORKFLOW_ORIGIN_NOT_EXIST("原工作流不存在", "Origin workflow does not exist"),
    WORKFLOW_COPY_ERROR("复制工作流失败", "Failed to copy workflow"),
    WORKFLOW_EXIST("工作流已存在", "Workflow already exists"),
    WORKFLOW_NAME_EXIST("工作流名称已存在", "Workflow name already exists"),
    WORKFLOW_RUNNING_EXIST("工作流正在运行中", "Workflow is running"),
    WORKFLOW_NOT_RUNNING("工作流已结束不能终止", "Workflow has ended and cannot be terminated"),
    WORKFLOW_CANCELING("工作流取消中", "Workflow is canceling"),
    WORKFLOW_CANCELLED_SUCCESS("工作流已经取消成功", "Workflow has been cancelled success"),
    WORKFLOW_CANCELLED_FAIL("工作流已经取消失败", "Workflow has been cancelled fail"),
    WORKFLOW_TERMINATE_NET_PROCESS_ERROR("工作流终止失败", "Workflow terminated failed"),
    WORKFLOW_END_NODE_OVERFLOW("截止节点不能大于工作流最大节点数", "EndNode can not more than workflow nodeNumber"),
    WORKFLOW_NOT_CLEAR("工作流不能清空，存在正在运行的工作流", "Workflow can not clear,workflow is running"),
    WORKFLOW_NODE_NOT_EXIST("工作流节点不存在", "Workflow node does not exist"),
    WORKFLOW_NODE_NOT_CACHE("工作流节点未缓存", "Workflow node not cached"),
    WORKFLOW_NODE_COUNT_CHECK("只支持运行一种算法", "Only one algorithm is supported"),
    WORKFLOW_NODE_SENDER_NOT_EXIST("工作流节点发起方不存在！", "Workflow node does not exist sender"),
    WORKFLOW_NODE_NOT_INPUT_EXIST("工作流节点输入未配置", "Workflow node input is not configured"),
    WORKFLOW_NODE_NOT_OUTPUT_EXIST("工作流节点输出未配置", "Workflow node output is not configured"),
    WORKFLOW_NODE_CODE_NOT_EXIST("工作流节点代码未配置", "Workflow node code is not configured"),
    WORKFLOW_NODE_NOT_RESOURCE_EXIST("工作流节点环境未配置", "Workflow node environment is not configured"),
    WORKFLOW_NODE_MODEL_NOT_EXIST("工作流节点模型未配置！", "The workflow node model is not configured!"),
    WORKFLOW_NODE_DATA_ROWS_CHECK("工作流节点多方数据行数不一致！", "The number of data rows in the workflow node is inconsistent!"),
    WORKFLOW_PRE_TASK_RESULT_NOT_EXIST("工作流前一个节点运行节点获取失败", "Workflow pre task result not exist"),
    WORKFLOW_NODE_RUNNING_FAIL("工作流节点运行失败!", "The workflow node failed to run!"),
    WORKFLOW_INDEX_COLUMN_NOT_EXIST("工作流节点索引列不存在", "Workflow node index column not exist"),
    WORKFLOW_NODE_INPUT_NOT_EXIST("工作流节点输入不存在", "Workflow node input not exist"),
    WORKFLOW_NODE_If_SENDER_ERROR("节点输入配置数据发起方不正确！", "The originator of the node input configuration data is incorrect!"),
    WORKFLOW_NODE_SENDER_MODEL_IDENTITY_STEP1_ERROR("节点输入配置任务发起方和模型不在一个组织内！", "Node input configuration task initiator and model are not in the same organization!"),
    WORKFLOW_NODE_SENDER_MODEL_IDENTITY_STEP2_ERROR("节点输入配置任务发起方和上个节点输出不在一个组织内！", "The node input configuration task initiator and the last node output are not in the same organization!"),

    WORKFLOW_NODE_TASK_RESULT_NOT_EXIST("工作流节点输入模型不存在", "Workflow node input model not exist"),
    WORKFLOW_NODE_MODEL_PATH_NOT_EXIST("工作流节点输入模型路径不存在", "Workflow node input model file path not exist"),
    WORKFLOW_FILE_DOWNLOAD_FAIL("工作流节点输入模型文件下载失败", "Workflow node model file download fail"),
    WORKFLOW_FILE_DOWNLOAD_TIMEOUT("工作流节点输入模型文件下载超时", "Workflow node model file download timeout"),
    WORKFLOW_FILE_DOWNLOAD_COMPRESSTYPE_ERROR("工作流节点输入模型文件下载压缩格式错误", "Workflow node model file download compressType error"),


    /**
     * rpc接口异常
     */
    RPC_INTERFACE_FAIL("调度服务接口异常，请稍后重试！", "The scheduling service interface is abnormal, please try again later!"),

    /**
     * 机构
     */
    ORGANIZATION_NOT_EXIST("机构不存在", "Organization does not exist"),
    ORGANIZATION_INFO_ERROR("连接组织节点服务不可达", "Organization can not connect"),
    ORGANIZATION_UNAVAILABLE_SENDER("任务的发起方组织不可用 {}", "The originating organization of the task is unavailable {}"),
    ORGANIZATION_UNAVAILABLE_DATA_PROVIDED("任务的数据提供方组织不可用 {}", "The data provider organization for the task is unavailable {}"),
    ORGANIZATION_UNAVAILABLE_OUTPUT("任务的结果输出方组织不可用 {}", "The result exporter organization of the task is unavailable {}"),
    ;

    private final String zh;
    private final String en;

    ErrorMsg(String zh, String en) {
        this.zh = zh;
        this.en = en;
    }

    public String getMsg() {
        return LanguageContext.getByLanguage(this.zh, this.en);
    }
}
