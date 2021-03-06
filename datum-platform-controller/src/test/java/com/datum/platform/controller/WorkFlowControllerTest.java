package com.datum.platform.controller;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.datum.platform.common.utils.WalletSignUtils;
import com.datum.platform.mapper.domain.AlgorithmClassify;
import com.datum.platform.mapper.domain.AlgorithmVariable;
import com.datum.platform.service.AlgService;
import com.datum.platform.service.utils.TreeUtils;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "dev")
@AutoConfigureMockMvc
public class WorkFlowControllerTest extends BaseControllerTest{

    // ----------------------向导模式创建计算流程为PSI的工作流----------------------------------------------
    @Test
    public void createWorkflowOfWizardModeCase4() throws Exception {
        JSONObject req = createWorkflow("chendai-flow-psi-wizard", "chendai-desc-psi-wizard", 1001L, 4L);
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/createWorkflowOfWizardMode", req.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase4Step1() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(1L, 1L, 1));
    }

    @Test
    public void setWorkflowOfWizardModeCase4Step1() throws Exception {
        String responseStr = getWorkflowOfWizardMode(1L, 1L, 1);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONObject psiInput = request.getJSONObject("psiInput");
        psiInput.put("identityId", "identity:4d7b5f1f114b43b682d9c73d6d2bc18e");
        JSONArray itemList = new JSONArray();
        itemList.add(createInputData("identity:4d7b5f1f114b43b682d9c73d6d2bc18e",
                "metadata:0x905e8163b76b661ef0b5b36231c07cc403a4a25af5d3746eb314613d4590d7e5",
                1, null, null));
        itemList.add(createInputData("identity:8003323d0d1248719be0d50e98e9666e",
                "metadata:0x5432ed28f3e61f1067f6f88a63a71b33076c8a686a574fe1312f99b56c2da9c8",
                1, null, null));
        psiInput.put("item", itemList);

        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase4Step2() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(1L, 1L, 2));
    }

    @Test
    public void setWorkflowOfWizardModeCase4Step2() throws Exception {
        String responseStr = getWorkflowOfWizardMode(1L, 1L, 2);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        request.put("commonResource", createResource(2,2,2048,6,5));
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase4Step3() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(1L, 1L, 3));
    }

    @Test
    public void setWorkflowOfWizardModeCase4Step3() throws Exception {
        String responseStr = getWorkflowOfWizardMode(1L, 1L, 3);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        request.put("commonOutput", createOutput(1, "identity:8003323d0d1248719be0d50e98e9666e", "identity:4d7b5f1f114b43b682d9c73d6d2bc18e"));
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    // ----------------------向导模式创建计算流程为线性训练的工作流（带psi）----------------------------------------------
    @Test
    public void createWorkflowOfWizardModeCase1() throws Exception {
        JSONObject req = createWorkflow("chendai-flow-linear-train-psi-wizard", "chendai-desc-linear-train-psi-wizard", 2010L, 1L);
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/createWorkflowOfWizardMode", req.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase1Step1() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(2L, 1L, 1));
    }

    @Test
    public void setWorkflowOfWizardModeCase1Step1() throws Exception {
        String responseStr = getWorkflowOfWizardMode(2L, 1L, 1);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONObject trainingInput = request.getJSONObject("trainingInput");
        trainingInput.put("isPsi", true);
        trainingInput.put("identityId", "identity:4d7b5f1f114b43b682d9c73d6d2bc18e");
        JSONArray itemList = new JSONArray();
        itemList.add(createInputData("identity:4d7b5f1f114b43b682d9c73d6d2bc18e",
                "metadata:0x905e8163b76b661ef0b5b36231c07cc403a4a25af5d3746eb314613d4590d7e5",
                1, "2,3,4", 28));
        itemList.add(createInputData("identity:8003323d0d1248719be0d50e98e9666e",
                "metadata:0x5432ed28f3e61f1067f6f88a63a71b33076c8a686a574fe1312f99b56c2da9c8",
                1, "5,6,7", 0));

        trainingInput.put("item", itemList);
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase1Step2() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(2L, 1L, 2));
    }

    @Test
    public void setWorkflowOfWizardModeCase1Step2() throws Exception {
        String responseStr = getWorkflowOfWizardMode(2L, 1L, 2);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        request.put("commonResource", createResource(2,2,2048,6,5));
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase1Step3() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(2L, 1L, 3));
    }

    @Test
    public void setWorkflowOfWizardModeCase1Step3() throws Exception {
        String responseStr = getWorkflowOfWizardMode(2L, 1L, 3);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        request.put("commonOutput", createOutput(1, "identity:8003323d0d1248719be0d50e98e9666e", "identity:4d7b5f1f114b43b682d9c73d6d2bc18e"));
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }



    // ----------------------向导模式创建计算流程为预测的工作流----------------------------------------------

    @Test
    public void createWorkflowOfWizardModeCase2() throws Exception {
        JSONObject req = createWorkflow("chendai-flow-2", "chendai-desc-2", 2010L, 2L);
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/createWorkflowOfWizardMode", req.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase2Step1() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(2L, 1L, 1));
    }

    @Test
    public void setWorkflowOfWizardModeCase2Step1() throws Exception {
        String responseStr = getWorkflowOfWizardMode(2L, 1L, 1);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONObject predictionInput = request.getJSONObject("predictionInput");
        predictionInput.put("isPsi", false);
        predictionInput.put("identityId", "identity:17c9cc15b6a14f858a96a633c3486f3d");
        JSONArray itemList = new JSONArray();
        itemList.add(createInputData("identity:17c9cc15b6a14f858a96a633c3486f3d",
                "metadata:0x48a648e1b728e3e61eeb148c827cec3bc22a197995706e89c1e5c23c5be4fb3b",
                1, "2,3,4", null));
        itemList.add(createInputData("identity:6a9df99adf8e48ed94bed3b53f9ea4f7",
                "metadata:0x3b4938ff6df23161f7ba77a798b9348ec757b37fb272f2cdc6a0b5998853ffd6",
                1, "5,6,7", null));
        predictionInput.put("item", itemList);
        predictionInput.put("model", createModel("metadata:0x0e4693a97c213d057cbbb69ae18a217628d943776592996a87e59c594bd095a8"));
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase2Step2() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(2L, 1L, 2));
    }

    @Test
    public void setWorkflowOfWizardModeCase2Step2() throws Exception {
        String responseStr = getWorkflowOfWizardMode(2L, 1L, 2);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        request.put("commonResource", createResource(2,4,2048,6,6));
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase2Step3() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(2L, 1L, 3));
    }

    @Test
    public void setWorkflowOfWizardModeCase2Step3() throws Exception {
        String responseStr = getWorkflowOfWizardMode(2L, 1L, 3);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        request.put("commonOutput", createOutput(1, "identity:17c9cc15b6a14f858a96a633c3486f3d", "identity:6a9df99adf8e48ed94bed3b53f9ea4f7"));
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    // ----------------------向导模式创建计算流程为训练，并预测的工作流----------------------------------------------

    @Test
    public void createWorkflowOfWizardModeCase3() throws Exception {
        JSONObject req = createWorkflow("cd-flow-linear-tp-psi-w", "cd-flow-linear-tp-psi-w-desc", 2010L, 3L);
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/createWorkflowOfWizardMode", req.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase3Step1() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(6L, 1L, 1));
    }

    @Test
    public void setWorkflowOfWizardModeCase3Step1() throws Exception {
        String responseStr = getWorkflowOfWizardMode(6L, 1L, 1);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONObject trainingInput = request.getJSONObject("trainingInput");
        trainingInput.put("isPsi", true);
        trainingInput.put("identityId", "identity:8003323d0d1248719be0d50e98e9666e");
        JSONArray itemList = new JSONArray();
        itemList.add(createInputData("identity:4d7b5f1f114b43b682d9c73d6d2bc18e",
                "metadata:0x905e8163b76b661ef0b5b36231c07cc403a4a25af5d3746eb314613d4590d7e5",
                1, "2,3,4", 28));
        itemList.add(createInputData("identity:8003323d0d1248719be0d50e98e9666e",
                "metadata:0x5432ed28f3e61f1067f6f88a63a71b33076c8a686a574fe1312f99b56c2da9c8",
                1, "5,2,3", 0));
        trainingInput.put("item", itemList);
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase3Step2() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(6L, 1L, 2));
    }

    @Test
    public void setWorkflowOfWizardModeCase3Step2() throws Exception {
        String responseStr = getWorkflowOfWizardMode(6L, 1L, 2);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONObject predictionInput = request.getJSONObject("predictionInput");
        predictionInput.put("isPsi", true);
        predictionInput.put("identityId", "identity:8003323d0d1248719be0d50e98e9666e");
        JSONArray itemList = new JSONArray();
        itemList.add(createInputData("identity:8003323d0d1248719be0d50e98e9666e",
                "metadata:0x6f2ebb118c49e344c94b4e403703b1d8367c8f12d6b72eae495b2c3c3d0ee4b3",
                1, "8,9,10", null));
        itemList.add(createInputData("identity:4d7b5f1f114b43b682d9c73d6d2bc18e",
                "metadata:0xd3886be7f8cca8a9bdcb0057c56b8e3da2d83886a9d3c68981b9ff6093d71899",
                1, "7,8,9", null));
        predictionInput.put("item", itemList);
        predictionInput.put("model", null); // 使用上个步骤的模型
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase3Step3() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(6L, 1L, 3));
    }

    @Test
    public void setWorkflowOfWizardModeCase3Step3() throws Exception {
        String responseStr = getWorkflowOfWizardMode(6L, 1L, 3);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONObject trainingAndPredictionResource =  request.getJSONObject("trainingAndPredictionResource");
        trainingAndPredictionResource.put("training", createResource(2,4,2048,100,6));
        trainingAndPredictionResource.put("prediction", createResource(2,8,2048,100,6));
        System.out.println(request.toJSONString());
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }

    @Test
    public void getWorkflowOfWizardModeCase3Step4() throws Exception {
        System.out.println("result = " + getWorkflowOfWizardMode(3L, 1L, 4));
    }

    @Test
    public void setWorkflowOfWizardModeCase3Step4() throws Exception {
        String responseStr = getWorkflowOfWizardMode(6L, 1L, 4);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONObject trainingAndPredictionOutput =  request.getJSONObject("trainingAndPredictionOutput");
        trainingAndPredictionOutput.put("training", createOutput(1, "identity:8003323d0d1248719be0d50e98e9666e", "identity:4d7b5f1f114b43b682d9c73d6d2bc18e"));
        trainingAndPredictionOutput.put("prediction", createOutput(1, "identity:8003323d0d1248719be0d50e98e9666e", "identity:4d7b5f1f114b43b682d9c73d6d2bc18e"));
        System.out.println("result = " + commonPostWithToken("/workflow/wizard/settingWorkflowOfWizardMode", request.toJSONString()));
    }



    // ----------------------专家模式创建的单节点训练工作流----------------------------------------------
    @Resource
    private AlgService algService;

    @Test
    public void createWorkflowOfExpertModeCase1() throws Exception {
        System.out.println("result = "  + createWorkflowOfExpertMode("chendai-flow-5"));
    }

    @Test
    public void getWorkflowSettingOfExpertModeCase1Step1() throws Exception {
        System.out.println("result = "  + getWorkflowSettingOfExpertMode(5L, 1L));
    }

    @Test
    public void settingWorkflowOfExpertModeCase1Step1() throws Exception {
        AlgorithmClassify root = algService.getAlgorithmClassifyTree(true);
        String responseStr = getWorkflowSettingOfExpertMode(5L, 1L);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONArray workflowNodeList = new JSONArray();
        JSONObject workflowNode = createWorkflowNode(1, TreeUtils.findSubTree(root, 2011L), false);
        workflowNodeList.add(workflowNode);
        request.put("workflowNodeList", workflowNodeList);

        System.out.println("req = " + request.toJSONString());

        System.out.println("result = "  + settingWorkflowOfExpertMode(request.toJSONString()));
    }

    // ----------------------专家模式创建的单节点预测工作流----------------------------------------------
    @Test
    public void createWorkflowOfExpertModeCase2() throws Exception {
        System.out.println("result = "  + createWorkflowOfExpertMode("chendai-flow-6"));
    }

    @Test
    public void getWorkflowSettingOfExpertModeCase2Step1() throws Exception {
        System.out.println("result = "  + getWorkflowSettingOfExpertMode(6L, 1L));
    }

    @Test
    public void settingWorkflowOfExpertModeCase2Step1() throws Exception {
        AlgorithmClassify root = algService.getAlgorithmClassifyTree(true);
        String responseStr = getWorkflowSettingOfExpertMode(6L, 1L);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONArray workflowNodeList = new JSONArray();
        JSONObject workflowNode = createWorkflowNode(1, TreeUtils.findSubTree(root, 2012L), false);
        workflowNodeList.add(workflowNode);
        request.put("workflowNodeList", workflowNodeList);
        System.out.println("result = "  + settingWorkflowOfExpertMode(request.toJSONString()));
    }

    // ----------------------专家模式创建的单节点预测工作流----------------------------------------------
    @Test
    public void createWorkflowOfExpertModeCase3() throws Exception {
        System.out.println("result = "  + createWorkflowOfExpertMode("chendai-flow-7"));
    }

    @Test
    public void getWorkflowSettingOfExpertModeCase3Step1() throws Exception {
        System.out.println("result = "  + getWorkflowSettingOfExpertMode(7L, 1L));
    }

    @Test
    public void settingWorkflowOfExpertModeCase7Step1() throws Exception {
        AlgorithmClassify root = algService.getAlgorithmClassifyTree(true);
        String responseStr = getWorkflowSettingOfExpertMode(7L, 1L);
        JSONObject response = JSONObject.parseObject(responseStr);
        JSONObject request = response.getJSONObject("data");
        JSONArray workflowNodeList = new JSONArray();
        JSONObject workflowNode1 = createWorkflowNode(1, TreeUtils.findSubTree(root, 2011L), false);
        JSONObject workflowNode2 = createWorkflowNode(2, TreeUtils.findSubTree(root, 2012L), true);
        workflowNodeList.add(workflowNode1);
        workflowNodeList.add(workflowNode2);
        request.put("workflowNodeList", workflowNodeList);
        System.out.println("result = "  + settingWorkflowOfExpertMode(request.toJSONString()));
    }

    // ----------------------通用功能----------------------------------------------

    @Test
    public void getWorkflowStats()throws Exception{
        System.out.println("result = "  + commonGetWithToken("/workflow/getWorkflowStats", emptyParameters));
    }

    @Test
    public void getWorkflowList()throws Exception{
        pageParameters.add("createMode", "1");
        pageParameters.add("begin", "1652371200000");
        pageParameters.add("end", "1652457600000");
        System.out.println("result = "  + commonGetWithToken("/workflow/getWorkflowList", pageParameters));
    }

    @Test
    public void getWorkflowVersionList()throws Exception{
        emptyParameters.add("workflowId", String.valueOf(1));
        System.out.println("result = "  + commonGetWithToken("/workflow/getWorkflowVersionList", emptyParameters));
    }

    @Test
    public void getWorkflowStatusOfExpertMode()throws Exception{
        System.out.println("result = "  + getWorkflowStatusOfExpertMode(7L, 1L));
    }

    @Test
    public void preparationStart()throws Exception{
        System.out.println("result = "  + preparationStart(1L, 1L));
    }

    @Test
    public void start()throws Exception{
        JSONObject req = new JSONObject();
        req.put("workflowId", 1);
        req.put("workflowVersion", 1);
        req.put("sign",  WalletSignUtils.signTypedDataV4(getWorkflowJson(user.getAddress()) , user.getEcKeyPair()));
        System.out.println("result = "  + commonPostWithToken("/workflow/start", req.toJSONString()));
    }

    @Test
    public void copy() throws Exception {
        JSONObject req = new JSONObject();
        req.put("workflowId", 1);
        req.put("workflowVersion", 1);
        req.put("workflowVersionName", "chendai-v2");
        System.out.println("result = "  + commonPostWithToken("/workflow/copyWorkflow", req.toJSONString()));
    }

    @Test
    public void getWorkflowRunTaskList()throws Exception{
        emptyParameters.add("workflowRunId", "14");
        System.out.println("result = "  + commonGetWithToken("/workflow/getWorkflowRunTaskList", emptyParameters));
    }


    private String getWorkflowJson(String address){
        String json = "{\"domain\":{\"name\":\"Moirae\"},\"message\":{\"address\":\"{}\"},\"primaryType\":\"sign\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"}],\"sign\":[{\"name\":\"address\",\"type\":\"string\"}]}}";
        return StrUtil.format(json, address);
    }

    private String preparationStart(Long workflowId, Long workflowVersion)throws Exception{
        emptyParameters.add("workflowId", String.valueOf(workflowId));
        emptyParameters.add("workflowVersion", String.valueOf(workflowVersion));
        return commonGetWithToken("/workflow/preparationStart", emptyParameters);
    }

    private String getWorkflowResultOfExpertMode(Long workflowId, Long workflowVersion, Integer nodeStep)throws Exception{
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
        parameters.add("workflowId", String.valueOf(workflowId));
        parameters.add("workflowVersion", String.valueOf(workflowVersion));
        parameters.add("nodeStep", String.valueOf(nodeStep));
        return commonGetWithToken("/workflow/expert/getWorkflowResultOfExpertMode", parameters);
    }

    private String getWorkflowLogOfExpertMode(Long workflowId, Long workflowVersion)throws Exception{
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
        parameters.add("workflowId", String.valueOf(workflowId));
        parameters.add("workflowVersion", String.valueOf(workflowVersion));
        return commonGetWithToken("/workflow/expert/getWorkflowLogOfExpertMode", parameters);
    }

    private String getWorkflowSettingOfExpertMode(Long workflowId, Long workflowVersion)throws Exception{
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
        parameters.add("workflowId", String.valueOf(workflowId));
        parameters.add("workflowVersion", String.valueOf(workflowVersion));
        return commonGetWithToken("/workflow/expert/getWorkflowSettingOfExpertMode", parameters);
    }

    private String getWorkflowStatusOfExpertMode(Long workflowId, Long workflowVersion) throws Exception{
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap();
        parameters.add("workflowId", String.valueOf(workflowId));
        parameters.add("workflowVersion", String.valueOf(workflowVersion));
        return commonGetWithToken("/workflow/expert/getWorkflowStatusOfExpertMode", parameters);
    }

    private String deleteWorkflow(Long workflowId) throws Exception {
        JSONObject req = new JSONObject();
        req.put("workflowId", workflowId);
        return commonPostWithToken("/workflow/deleteWorkflow", req.toJSONString());
    }

    private String createWorkflowOfExpertMode(String workflowName) throws Exception {
        JSONObject req = new JSONObject();
        req.put("workflowName", workflowName);
        return commonPostWithToken("/workflow/expert/createWorkflowOfExpertMode", req.toJSONString());
    }

    public String settingWorkflowOfExpertMode(String reqBody) throws Exception {
        return commonPostWithToken("/workflow/expert/settingWorkflowOfExpertMode", reqBody);
    }

    private String getWorkflowOfWizardMode(Long workflowId, Long workflowVersion, Integer step) throws Exception {
        String result = mvc.perform(get("/workflow/wizard/getWorkflowSettingOfWizardMode")
                        .param("workflowId", String.valueOf(workflowId))
                        .param("workflowVersion", String.valueOf(workflowVersion))
                        .param("step", String.valueOf(step))
                        .header("Accept-Language","zh")
                        .header("Access-Token",accessToken)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsString();
        return result;
    }

    private JSONObject createResource(Integer costCpu, Integer costGpu, Integer costMem, Integer costBandwidth, Integer runTime){
        JSONObject resource = new JSONObject();
        resource.put("costCpu", costCpu);
        resource.put("costGpu", costGpu);
        resource.put("costMem", costMem);
        resource.put("costBandwidth", costBandwidth);
        resource.put("runTime", runTime);
        return resource;
    }

    private JSONObject createWorkflow(String workflowName, String workflowDesc, Long algorithmId, Long calculationProcessId){
        JSONObject workflow = new JSONObject();
        workflow.put("workflowName", workflowName);
        workflow.put("workflowDesc", workflowDesc);
        workflow.put("algorithmId", algorithmId);
        workflow.put("calculationProcessId", calculationProcessId);
        return workflow;
    }

    private JSONObject createInputData(String identityId, String metaDataId, Integer keyColumn, String dataColumnIds, Integer dependentVariable){
        JSONObject input = new JSONObject();
        input.put("identityId", identityId);
        input.put("metaDataId", metaDataId);
        input.put("keyColumn", keyColumn);
        input.put("dataColumnIds",dataColumnIds);
        input.put("dependentVariable", dependentVariable);
        return input;
    }

    private JSONObject createOutput(int storePattern, String ... identityIds) {
        JSONObject commonOutput = new JSONObject();
        commonOutput.put("storePattern", storePattern);
        JSONArray identityIdArray = new JSONArray();
        for (String identityId: identityIds) {
            identityIdArray.add(identityId);
        }
        commonOutput.put("identityId", identityIdArray);
        return commonOutput;
    }

    private JSONObject createModel(String metadataId) {
        JSONObject model = new JSONObject();
        model.put("metaDataId", metadataId);
        model.put("metaDataId", "fromPreNodeOutput");
        return model;
    }

    private JSONObject createEntryModel() {
        JSONObject model = new JSONObject();
        model.put("metaDataId", "fromPreNodeOutput");
        return model;
    }

    private JSONObject createWorkflowNode(int nodeStep, AlgorithmClassify algorithmClassify, boolean modelInputFromPreStep) {
        JSONObject workflowNode = new JSONObject();
        workflowNode.put("nodeName", algorithmClassify.getName());
        workflowNode.put("algorithmId", algorithmClassify.getId());
        workflowNode.put("nodeStep", nodeStep);
        // nodeCode
        JSONObject nodeCode = new JSONObject();
        JSONObject code = new JSONObject();
        code.put("calculateContractCode", algorithmClassify.getAlg().getAlgorithmCode().getCalculateContractCode());
        code.put("dataSplitContractCode", algorithmClassify.getAlg().getAlgorithmCode().getDataSplitContractCode());
        code.put("editType", algorithmClassify.getAlg().getAlgorithmCode().getEditType());
        JSONArray variableList = new JSONArray();
        for (AlgorithmVariable algorithmVariable : algorithmClassify.getAlg().getAlgorithmVariableList()) {
            JSONObject variable = new JSONObject();
            variable.put("varKey", algorithmVariable.getVarKey());
            variable.put("varType", algorithmVariable.getVarType().getValue());
            variable.put("varValue", algorithmVariable.getVarValue());
            variable.put("varDesc", algorithmVariable.getVarDesc());
            variableList.add(variable);
        }
        nodeCode.put("code", code);
        nodeCode.put("variableList", variableList);
        workflowNode.put("nodeCode", nodeCode);
        // nodeOutput
        workflowNode.put("nodeOutput", createOutput(1, "identity:17c9cc15b6a14f858a96a633c3486f3d", "identity:6a9df99adf8e48ed94bed3b53f9ea4f7"));
        // resource
        workflowNode.put("resource", createResource(4,4,2048,6,6));
        // nodeInput
        JSONObject nodeInput = new JSONObject();
        nodeInput.put("isPsi", false);
        nodeInput.put("identityId", "identity:17c9cc15b6a14f858a96a633c3486f3d");
        JSONArray itemList = new JSONArray();
        itemList.add(createInputData("identity:17c9cc15b6a14f858a96a633c3486f3d",
                "metadata:0x48a648e1b728e3e61eeb148c827cec3bc22a197995706e89c1e5c23c5be4fb3b",
                1, "2,3,4", 10));
        itemList.add(createInputData("identity:6a9df99adf8e48ed94bed3b53f9ea4f7",
                "metadata:0x3b4938ff6df23161f7ba77a798b9348ec757b37fb272f2cdc6a0b5998853ffd6",
                1, "5,6,7", 0));
        nodeInput.put("dataInputList", itemList);
        if(algorithmClassify.getAlg().getInputModel()){
            nodeInput.put("inputModel", algorithmClassify.getAlg().getInputModel());
            if(!modelInputFromPreStep){
                nodeInput.put("model", createModel("metadata:0x0e4693a97c213d057cbbb69ae18a217628d943776592996a87e59c594bd095a8"));
            }else{
                nodeInput.put("model", createEntryModel());
            }

        }
        workflowNode.put("nodeInput", nodeInput);
        return workflowNode;
    }
}
