package com.datum.platform.chain.platon.config;

import com.datum.platform.chain.platon.enums.Web3jProtocolEnum;
import com.platon.parameters.NetworkParameters;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Locale;

@Component
@ConfigurationProperties(prefix="chain.platon")
@Data
public class PlatONProperties {
    private long chainId;
    private String hrp;
    private String datumNetworkPayAddress;
    private String uniswapV2Router02;
    private Web3jProtocolEnum web3jProtocol;
    private List<String> web3jAddresses;

    private String chainName;
    private String rpcUrl;
    private String blockExplorerUrl;
    private String dexUrl;
    private String symbol;

    public void setDatumNetworkPayAddress(String datumNetworkPayAddress){
        this.datumNetworkPayAddress = datumNetworkPayAddress.toLowerCase();
    }
    public void setUniswapV2Router02(String uniswapV2Router02){
        this.uniswapV2Router02 = uniswapV2Router02.toLowerCase();
    }

    @PostConstruct
    public void init(){
        NetworkParameters.init(chainId,hrp);
    }
}
