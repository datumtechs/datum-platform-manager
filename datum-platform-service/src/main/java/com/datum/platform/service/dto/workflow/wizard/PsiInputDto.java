package com.datum.platform.service.dto.workflow.wizard;

import com.datum.platform.service.dto.workflow.common.DataInputDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@ApiModel(value = "工作流节点输入请求对象")
public class PsiInputDto {

    @ApiModelProperty(value = "发起方的组织的身份标识Id", required = true)
    @NotBlank(message = "{task.sender.NotBlank}")
    private String identityId;

    @ApiModelProperty(value = "向导模式下PSI元数据输入")
    @Size(message = "{task.dataInput.size.equal.2}", min = 2, max = 2)
    private List<DataInputDto> item;
}
