package org.seckill.service;

import com.alibaba.druid.filter.AutoLoad;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
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
    //测试代码完整逻辑
    @Test
    public void testSeckillLogic() throws Exception {
        long id = 1001;
        Exposer exposer = secKillService.exportSeckillUrl(id);
        if(exposer.isExposed()){
            logger.info("exposer={}",exposer);
            long phone = 13502171127L;
            String md5 = exposer.getMd5();
            try{
                SecKillExecution execution = secKillService.executeSecKill(id,phone,md5);
                logger.info("result={}",execution);
            }catch(RepeatKillException e){
                logger.error(e.getMessage());
            }catch(SecKillCloseException e){
                logger.error(e.getMessage());
            }
        }else{
            //秒杀未开启
            logger.warn("exposer={}",exposer);
        }


    }

    @Test
    public void testExecuteSecKill() throws Exception {
        long id = 1000;
        long phone = 13502171127L;
        String md5 = "97739ff6f55a7eda84a696e6db2f63f9";
        try{
            SecKillExecution execution = secKillService.executeSecKill(id,phone,md5);
            logger.info("result={}",execution);
        }catch(RepeatKillException e){
            logger.error(e.getMessage());
        }catch(SecKillCloseException e){
            logger.error(e.getMessage());
        }



    }
    @Test
    public void testExecuteSecKillProcedure(){
        long seckillId = 1001;
        long phone = 1368011101;
        Exposer exposer = secKillService.exportSeckillUrl(seckillId);
        if(exposer.isExposed()){
            String md5 = exposer.getMd5();
            SecKillExecution ske = secKillService.executeSecKillProcedure(seckillId,phone,md5);
            logger.info(ske.getStateInfo());
        }

    }
}