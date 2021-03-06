package com.datum.platform.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.datum.platform.grpc.constant.GrpcConstant;
import com.datum.platform.manager.DataSyncManager;
import com.datum.platform.mapper.domain.DataSync;
import com.datum.platform.service.DataSyncService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @Author liushuyu
 * @Date 2022/1/5 14:05
 * @Version
 * @Desc
 */

@Service
public class DataSyncServiceImpl implements DataSyncService {

    @Resource
    private DataSyncManager dataSyncManager;

    @Override
    public DataSync getDataSyncByType(String dataSyncType) {
        return dataSyncManager.getOneByType(dataSyncType);
    }

    @Override
    public boolean updateDataSyncByType(DataSync dataSync) {
        LambdaUpdateWrapper<DataSync> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(DataSync::getDataType, dataSync.getDataType());
        return dataSyncManager.update(dataSync, updateWrapper);
    }

    @Override
    public boolean insertDataSync(DataSync dataSync) {
        return dataSyncManager.save(dataSync);
    }

    /**
     * @param dataType              需要同步的类型
     * @param grpcFunction          消费同步时间，调用grpc接口返回数据列表List<T>
     * @param bizProcessor          消费数据列表List<T>
     * @param latestSyncedGenerator 生成新的同步时间
     * @param <T>                   grpc接口返回的数据类型
     */
    @Override
    public <T> void sync(String dataType,
                         String dataTypeDesc,
                         Function<Long, List<T>> grpcFunction,
                         Consumer<List<T>> bizProcessor,
                         Function<List<T>, Long> latestSyncedGenerator) {
        //1.根据dataType同步类型获取新的同步时间DataSync
        DataSync dataSyncByType = this.getDataSyncByType(dataType);
        if (dataSyncByType == null) {//获取失败，则插入一条新的数据
            dataSyncByType = new DataSync();
            dataSyncByType.setDataType(dataType);
            dataSyncByType.setLatestSynced(0);
            dataSyncByType.setInfo(dataTypeDesc);
            this.insertDataSync(dataSyncByType);
        }
        long latestSynced = dataSyncByType.getLatestSynced();
        List<T> grpcResponseList;
        do {
            //2.根据新的同步时间latestSynced获取分页列表grpcResponseList
            grpcResponseList = grpcFunction.apply(latestSynced > 1000 ? latestSynced - 1000 : latestSynced );
            if (CollUtil.isEmpty(grpcResponseList)) {
                break;
            }

            //3.根据分页列表grpcResponseList实现实际业务逻辑
            bizProcessor.accept(grpcResponseList);

            //4.根据分页列表grpcResponseList获取最新的同步时间latestSynced
            latestSynced = latestSyncedGenerator.apply(grpcResponseList);
            //5.将最新的同步时间latestSynced存到数据库中
            dataSyncByType.setLatestSynced(latestSynced);
            this.updateDataSyncByType(dataSyncByType);
        } while (grpcResponseList.size() == GrpcConstant.PAGE_SIZE);//如果小于pageSize说明是最后一批了
    }

}
