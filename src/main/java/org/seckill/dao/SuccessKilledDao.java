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
     * @return 插入的行数
     */
    int insertSuccessKilled(long secKillId,long userPhone);

    /**
     * 根据id查询SucessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuceessKilled queryByIdWithSecKill(long seckillId);
}
