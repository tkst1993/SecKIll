package org.seckill.dao;

import org.apache.ibatis.annotations.Param;
import org.seckill.entity.SuceessKilled;

/**
 * Created by lenovo on 2016/6/18.
 */
public interface SuccessKilledDao {
    /**
     * 插入购买明细，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return 插入的行数
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据id查询SucessKilled并携带秒杀产品对象实体
     * @param seckillId
     * @return
     */
    SuceessKilled queryByIdWithSecKill(@Param("seckillId") long seckillId,@Param("userPhone") long userPhone);
}
