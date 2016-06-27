package org.seckill.service;

import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;

import java.util.List;

/**
 * 业务接口
 * 1、方法定义粒度 2、参数 3、返回类型
 * Created by tangke on 2016/6/27.
 */
public interface SecKillService {
    /**
     * 查询所有秒杀记录
     * @return
     */
    List<SecKill> getSecKillList();

    /**
     * 查询单个秒杀记录
     * @param seckillId
     * @return
     */
    SecKill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址 ，否则输出系统时间和秒杀时间
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀操作
     * @param seckillId
     * @param userPhone
     * @param md5
     */
    SecKillExecution executeSecKill(long seckillId, long userPhone, String md5) throws SecKillException,RepeatKillException,SecKillCloseException;

}
