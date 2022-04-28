package com.moirae.rosettaflow.task;

import cn.hutool.core.date.DateUtil;
import com.moirae.rosettaflow.chain.platon.contract.IUniswapV2FactoryContract;
import com.moirae.rosettaflow.chain.platon.contract.IUniswapV2PairContract;
import com.moirae.rosettaflow.mapper.domain.Token;
import com.moirae.rosettaflow.service.DataService;
import com.platon.tuples.generated.Tuple3;
import com.zengtengpeng.annotation.Lock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.List;

@ConditionalOnProperty(name="dev.quartz", havingValue="true")
@Slf4j
@Component
public class SyncTokenInfoFromDexTask {

    @Resource
    private DataService dataService;
    @Resource
    private IUniswapV2FactoryContract uniswapV2FactoryDao;
    @Resource
    private IUniswapV2PairContract uniswapV2PairDao;

    @Scheduled(fixedDelay = 60 * 1000)
    @Lock(keys = "SyncTokenInfoFromDexTask")
    public void run() {
        long begin = DateUtil.current();
        try {
            List<String> tokenList = dataService.listTokenId();
            tokenList.forEach(item -> {
                try {
                    if(!item.equals(uniswapV2FactoryDao.WETH())){
                        sync(item);
                    }
                } catch (Exception e){
                    log.error("DataToken的dex信息同步, 明细失败：{}",item, e);
                }
            });
        } catch (Exception e) {
            log.error("DataToken的dex信息同步失败原因：{}", e.getMessage(), e);
        }
        log.info("DataToken的dex信息同步，总耗时:{}ms", DateUtil.current() - begin);
    }

    private void sync(String tokenAddress){
        String pairAddress = uniswapV2FactoryDao.getPair(tokenAddress);
        if("0x0000000000000000000000000000000000000000".equals(pairAddress) || "0x0".equals(pairAddress)){
            return;
        }

        Tuple3<BigInteger, BigInteger, BigInteger>  tuple3 = uniswapV2PairDao.getReserves(pairAddress);
        if(tuple3.getValue1().compareTo(BigInteger.ZERO) == 0 || tuple3.getValue2().compareTo(BigInteger.ZERO) == 0){
            return;
        }

        Token token = new Token();
        token.setAddress(tokenAddress);
        token.setIsAddLiquidity(true);
        token.setPrice(getPrice(tuple3.getValue1(), tuple3.getValue2()));
        dataService.updateTokenById(token);
    }

    private String getPrice(BigInteger wEth, BigInteger token) {
        BigDecimal reserve0 = new BigDecimal(wEth);
        BigDecimal reserve1 = new BigDecimal(token);
        return reserve0.divide(reserve1, 3,  RoundingMode.HALF_DOWN).toString();
    }
}
