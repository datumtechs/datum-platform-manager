package com.moirae.rosettaflow.chain.platon;

import com.moirae.rosettaflow.chain.platon.contract.evm.MetisPay;
import com.moirae.rosettaflow.chain.platon.utils.AddressUtils;
import com.platon.bech32.Bech32;
import com.platon.protocol.core.methods.response.TransactionReceipt;
import org.junit.jupiter.api.Test;

public class MetisPlayTest extends BaseContractTest {

    private String address = AddressUtils.hexToBech32("0x7eB718524D1eCcEeF89196BC8315317bf8B5a05a");

    @Test
    public void addWhitelist() throws Exception{
        MetisPay contract = load();
        TransactionReceipt transactionReceipt = contract.addWhitelist("lat1fgpxywxwaahhesxkqw77y9gtxrpmsyvj8l4fqs").send();
        System.out.println(transactionReceipt.getTransactionHash());
    }

    @Test
    public void deleteWhitelist() throws Exception{
        MetisPay contract = load();
        TransactionReceipt transactionReceipt = contract.deleteWhitelist("lat15nv9adgf5yljm3ccqrkk2tykpat3kfg25tr9g0").send();
        System.out.println(transactionReceipt.getTransactionHash());
    }

    @Test
    public void whitelist() throws Exception{
        MetisPay contract = load();
        System.out.println(contract.whitelist("lat1cy2uat0eukfrxv897s5s8lnljfka5ewjtnrfhx").send());
    }

    @Test
    public void deploy() throws Exception{
        MetisPay contract = MetisPay.deploy(web3j, credentials, gasProvider).send();
        System.out.println(contract.getContractAddress());
        System.out.println(Bech32.addressDecodeHex(contract.getContractAddress()));
        // lat1r62d243ehjharw647xadnn5matu0d46f3t8v9y
        // 0x1e94d55639bcafd1bb55f1bad9ce9beaf8f6d749
    }

    private MetisPay load(){
        return MetisPay.load(address, web3j, credentials, gasProvider);
    }
}
