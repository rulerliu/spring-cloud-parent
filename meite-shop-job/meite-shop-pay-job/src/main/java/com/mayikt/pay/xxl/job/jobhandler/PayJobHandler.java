package com.mayikt.pay.xxl.job.jobhandler;

import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: liuwq
 * @date: 2019/4/14 0014 12:47
 * @version: V1.0
 * @Copyright:该项目“基于SpringCloud2.x构建微服务电商项目”由每特教育|蚂蚁课堂版权所有，未经过允许的情况下， 私自分享视频和源码属于违法行为。
 */
@Component
@JobHandler("payJobHandler")
@Slf4j
public class PayJobHandler extends IJobHandler {

    @Override
    public ReturnT<String> execute(String s) throws Exception {
        log.info(">>>使用任务调度实现自动化对账...");
        return SUCCESS;
    }
}
