package org.seckill.dao;

import org.seckill.entity.SecKill;

import java.util.Date;
import java.util.List;

/**
 * Created by tangke on 2016/6/13.
 */
public interface SecKillDao {
    /**
     * 减库存
     * @param seckillId
     * @param killTime
     * @return
     */
    int reduceNumber(long seckillId,Date killTime);

    /**
     *根据id查询秒杀对象
     * @param seckillId
     * @return
     */
    SecKill queryById(long seckillId);

    /**
     * 根据偏移量查询商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<SecKill> queryAll(int offset, int limit);
}
