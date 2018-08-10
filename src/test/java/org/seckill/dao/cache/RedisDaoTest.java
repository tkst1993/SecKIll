package org.seckill.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dao.SecKillDao;
import org.seckill.entity.SecKill;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by tangke on 2018/7/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//告诉Junit4 Spring配置文件
@ContextConfiguration(("classpath:spring/spring-dao.xml"))
public class RedisDaoTest{
    private long id = 1001;
    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SecKillDao secKillDao;

    @Test
    public void testSeckill() throws Exception {
        SecKill seckill = redisDao.getSeckill(id);
        if(seckill == null){
            seckill = secKillDao.queryById(id);
            if(seckill != null){
                String result = redisDao.putSeckill(seckill);
                System.out.print(result);
                seckill = redisDao.getSeckill(id);
                System.out.print(seckill);
            }

        }

    }


}