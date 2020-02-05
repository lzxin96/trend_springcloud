package cn.how2j.trend.job;

import cn.how2j.trend.pojo.Index;
import cn.how2j.trend.service.IndexDataService;
import cn.how2j.trend.service.IndexService;
import cn.hutool.core.date.DateUtil;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.List;

/**
 * 任务类
 * 同时刷新指数代码和指数数据
 * @Author
 */
public class IndexDataSyncJob extends QuartzJobBean {
    @Autowired
    private IndexService indexService;
    @Autowired
    private IndexDataService indexDataService;

    /**
     * 需要执行的任务
     * @param Context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext Context) throws JobExecutionException {
        System.out.println("定时启动：" + DateUtil.now());
        List<Index> indexes = indexService.fresh();
        for (Index index : indexes){
            indexDataService.fresh(index.getCode());
        }
        System.out.println("定时任务结束：" + DateUtil.now());
    }
}
