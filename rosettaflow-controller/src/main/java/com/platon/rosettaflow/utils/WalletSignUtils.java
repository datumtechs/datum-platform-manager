package com.platon.rosettaflow.utils;

import com.platone.sdk.utlis.Bech32;
import org.apache.tomcat.util.codec.binary.Base64;
import org.web3j.crypto.Credentials;
import org.web3j.crypto.ECKeyPair;
import org.web3j.crypto.Keys;
import org.web3j.crypto.Sign;
import org.web3j.utils.Numeric;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.SignatureException;
import java.util.Arrays;

import static com.platon.rosettaflow.utils.AddressChangeUtils.convertBits;

/**
 * @author hudenian
 * @date 2021/8/10
 * @description 钱包签名验证签名工具类
 */
public class WalletSignUtils {
    /**
     * 签名
     *
     * @param message   签名消息
     * @param ecKeyPair 密钥对
     * @return 签名值
     */
    public static String sign(String message, ECKeyPair ecKeyPair) {
        Sign.SignatureData signatureData = Sign.signMessage(message.getBytes(StandardCharsets.UTF_8), ecKeyPair);

        byte[] sigData = new byte[65];
        sigData[0] = signatureData.getV()[0];
        System.arraycopy(signatureData.getR(), 0, sigData, 1, 32);
        System.arraycopy(signatureData.getS(), 0, sigData, 33, 32);
        return new String(Base64.encodeBase64(sigData), StandardCharsets.UTF_8);
    }

    /**
     * 验签
     *
     * @param message 签名明文
     * @param signMsg 签名信息
     * @param address 签名人地址
     * @return 验证成功失败标识
     */
    public static boolean verifySign(String message, String signMsg, String address) {
        byte[] bs = Base64.decodeBase64(signMsg);
        Sign.SignatureData signatureData = new Sign.SignatureData(bs[0], Arrays.copyOfRange(bs, 1, 33), Arrays.copyOfRange(bs, 33, 65));

        BigInteger bigInteger;
        try {
            bigInteger = Sign.signedMessageToKey(message.getBytes(StandardCharsets.UTF_8), signatureData);
        } catch (SignatureException e) {
            e.printStackTrace();
            return false;
        }
        String tmpAddress = Keys.getAddress(bigInteger);
        return Numeric.cleanHexPrefix(address).equals(Numeric.cleanHexPrefix(tmpAddress));
    }

    public static void main(String[] args) {
        try {
            Credentials credentials = Credentials.create("b4b282bf890abfc11e9b1832a2735f4c77fb3267978723034f84beb4e71fdf79");
            String address = credentials.getAddress();
            address = DataChangeUtils.bytesToHex(Bech32.addressDecode(address));
            System.out.println("钱包地址为>>>" + address);
            String signStr = sign(address, credentials.getEcKeyPair());
            System.out.println("钱包地址签名后的内容为>>>" + signStr);

            System.out.println("验证签名的结果为>>>" + verifySign(address, signStr, address));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
