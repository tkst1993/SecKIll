package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

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
        long id = 1000;
        SecKill seckill = seckillDao.queryById(id);
        System.out.println(seckill.getName());
        System.out.println(seckill);
        System.out.println("你好");
    }

    @Test
    public void testQueryById() throws Exception {

    }

    @Test
    public void testQueryAll() throws Exception {

    }
}