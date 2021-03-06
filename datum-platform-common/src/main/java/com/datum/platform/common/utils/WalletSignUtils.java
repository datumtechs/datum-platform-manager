package com.datum.platform.common.utils;

import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Sign;
import org.web3j.crypto.StructuredDataEncoder;

import javax.xml.bind.DatatypeConverter;
import java.io.IOException;
import java.util.Map;

/**
 * @author hudenian
 * @date 2021/8/10
 * @description 钱包签名验证签名工具类
 */

@Slf4j
public class WalletSignUtils {

    /**
     * 验证Sign Typed Data v4 的签名
     *
     * @param jsonMessage json格式签名明文
     * @param signMsg     签名数据
     * @param address     hrp钱包地址
     * @return 是否成功标识
     */
    public static boolean verifyTypedDataV4(String jsonMessage, String signMsg, String address) throws IOException {
        StructuredDataEncoder dataEncoder = new StructuredDataEncoder(jsonMessage);
        Map<Integer, String> addresses = CryptoUtils.ecRecover(signMsg, dataEncoder.hashStructuredData());
        return addresses.toString().contains(AddressChangeUtils.convert0xAddress(address).toLowerCase());
    }

    /**
     * 对数据进行eth_signTypedData_v4签名
     *
     * @param jsonMessage 签名数据结构体
     * @param ecKeyPair   密钥对
     * @return 签名字符串
     */
    public static String signTypedDataV4(String jsonMessage, ECKeyPair ecKeyPair) throws IOException {
        StrUtil.replace(jsonMessage, "\\\"", "\"");
        StructuredDataEncoder encoder = new StructuredDataEncoder(jsonMessage);
        byte[] hash = encoder.hashStructuredData();
        Sign.SignatureData signatureData = Sign.signMessage(hash, ecKeyPair, false);
        byte[] bytesValue = ArrayUtils.addAll(signatureData.getR(), signatureData.getS());
        bytesValue = ArrayUtils.addAll(bytesValue, signatureData.getV());
        return "0x" + DatatypeConverter.printHexBinary(bytesValue).toLowerCase();
    }

    public static void main(String[] args) {
//        String uuid = "834733e3fc0f4c95a191aeb1ceed17eb";
//        //登录签名
//        String json = "{\"domain\":{\"name\":\"Moirae\"},\"message\":{\"key\":\"{}\",\"desc\":\"Welcome to Moirae!\"},\"primaryType\":\"Login\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"}],\"Login\":[{\"name\":\"key\",\"type\":\"string\"},{\"name\":\"desc\",\"type\":\"string\"}]}}";
//        //启动工作流签名
////        String json = "{\"domain\":{\"name\":\"Moirae\"},\"message\":{\"address\":\"d6a151c0703d47e6baa068700e8c5381\"},\"primaryType\":\"sign\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"}],\"sign\":[{\"name\":\"key\",\"type\":\"string\"}]}}";
//
//        try {
//            json = StrUtil.format(json, uuid);
//            System.out.println("加密的json字符串为>>>" + json);
//            Credentials credentials = Credentials.create("c065dc203f33c3fbe003fed50d917898cc69b09c133bb109257d8cd21841672a");
//            System.out.println("钱包hrp地址>>>" + credentials.getAddress());
//            System.out.println("钱包0x地址>>>" + AddressChangeUtils.convert0xAddress(credentials.getAddress()));
//
//            System.out.println("签名结果>>>" + signTypedDataV4(json, credentials.getEcKeyPair()));
//
//            System.out.println("验证签名结果>>>" + verifyTypedDataV4(
//                    json,
//                    "0xfb71e85fb91026933d0057c263b549197e4d93e8a955728892f3ae9cd4cc6c4973c235ecc4dc2bf6927006b53f42bc3f56110649f040357489f41b3cd9c10af71c",
//                    "lax1j0q78v8g9l94pkwyk3tgk0vfy5ukdz3q2e7wqa"));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        createLoginSign("108068000da24e58b4d13104e439be53",Credentials.create("c065dc203f33c3fbe003fed50d917898cc69b09c133bb109257d8cd21841672a"));
    }

    /**
     * 创建登录签名
     * @param nonce nonce，登录前需要先获取nonce {@link UserController#getLoginNonce(String)}
     * @param credentials 钱包凭证
     */
    public static String createLoginSign(String nonce,Credentials credentials){
        log.info("address>>>" + credentials.getAddress());
        //登录签名
        String json = "{\"domain\":{\"name\":\"Moirae\"},\"message\":{\"key\":\"{}\",\"desc\":\"Welcome to Moirae!\"},\"primaryType\":\"Login\",\"types\":{\"EIP712Domain\":[{\"name\":\"name\",\"type\":\"string\"}],\"Login\":[{\"name\":\"key\",\"type\":\"string\"},{\"name\":\"desc\",\"type\":\"string\"}]}}";
        try {
            json = StrUtil.format(json, nonce);
            log.info("signMessage>>>" + json);
            String sign = signTypedDataV4(json, credentials.getEcKeyPair());
            log.info("sign>>>" + sign);
            return sign;
        } catch (Exception e) {
            log.error("创建登录签名失败！",e);
        }
        return null;
    }
}
