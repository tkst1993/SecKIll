package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by lenovo on 2016/6/23.
 */
@RunWith(SpringJUnit4ClassRunner.class)

//告诉Junit4 Spring配置文件
@ContextConfiguration(("classpath:spring/spring-dao.xml"))
public class SecKillDaoTest {
    //注入dao实现类依赖
    @Resource
    private SecKillDao seckillDao;
    @Test
    public void testReduceNumber() throws Exception {
        Date killTime =new Date();
        int updateCount = seckillDao.reduceNumber(1000L,killTime);
        System.out.println("updateCount:"+updateCount);

    }

    @Test
    public void testQueryById() throws Exception {
        long id = 1000;
        SecKill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        System.out.println("你好");

    }

    @Test
    public void testQueryAll() throws Exception {
        List<SecKill> seckills = seckillDao.queryAll(0,100);
        for(SecKill seckill:seckills){
            System.out.println(seckill);
        }
    }
    @Test
    public void insertSeckillTest() throws Exception{
        String name="2000元秒杀外星人电脑";
        int i = seckillDao.insertSeckillTest(name);
        System.out.println("受影响行数："+i);

    }
}