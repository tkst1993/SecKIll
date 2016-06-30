package org.seckill.service;

import com.alibaba.druid.filter.AutoLoad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.entity.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by tangke on 2016/6/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-service.xml","classpath:spring/spring-dao.xml"})
public class SecKillServiceTest {
    private final Logger logger = LoggerFactory.getLogger(SecKillServiceTest.class);
    @Autowired
    private SecKillService secKillService;
    @Test
    public void testGetSecKillList() throws Exception {
        List<SecKill> list = secKillService.getSecKillList();
        logger.info("list={}",list);
    }

    @Test
    public void testGetById() throws Exception {
        long id = 1000;
        SecKill sk = secKillService.getById(id);
        logger.info("seckill={}",sk);
    }

    @Test
    public void testExportSeckillUrl() throws Exception {
        System.out.println("test");

    }

    @Test
    public void testExecuteSecKill() throws Exception {

    }
}