package org.seckill.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SuceessKilled;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * Created by tangke on 2016/6/26.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(("classpath:spring/spring-dao.xml"))
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void testInsertSuccessKilled() throws Exception {
        long id =1001L;
        long phoneNum = 13502181181L;
        int insertCount = successKilledDao.insertSuccessKilled(id,phoneNum);
        System.out.println("insertCount:"+insertCount);
    }

    @Test
    public void testQueryByIdWithSecKill() throws Exception {
        long id =1001L;
        long phoneNum = 13502181182L;
        SuceessKilled sk = successKilledDao.queryByIdWithSecKill(id,phoneNum);
        System.out.println("testQueryByIdWithSecKill:"+sk);
        System.out.println("seckill"+sk.getSeckill());
    }
}