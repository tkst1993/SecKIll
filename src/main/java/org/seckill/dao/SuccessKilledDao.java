package org.seckill.dao;

import org.seckill.entity.SuceessKilled;

/**
 * Created by lenovo on 2016/6/18.
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细，可过滤重复
     * @param secKillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(long secKillId,long userPhone);
}
