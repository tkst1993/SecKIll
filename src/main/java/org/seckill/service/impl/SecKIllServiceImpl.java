package org.seckill.service.impl;

import org.apache.commons.collections.MapUtils;
import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKilledDao;
import org.seckill.dao.cache.RedisDao;
import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.entity.SuceessKilled;
import org.seckill.enums.SecKillStateEnum;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;
import org.seckill.service.SecKillService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;

import javax.xml.ws.soap.Addressing;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by tangke on 2016/6/28.
 */
@Service
public class SecKIllServiceImpl implements SecKillService {
    private Logger logger= LoggerFactory.getLogger(SecKIllServiceImpl.class);
    @Autowired
    private SecKillDao seckillDao;
    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;
    //md5盐值字符串
    private final String salt = "erwqjinwewrjinvn324332#$@@%@#";

    public List<SecKill> getSecKillList() {
        return seckillDao.queryAll(0,4);
    }

    public SecKill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(@PathVariable("seckillId")long seckillId) {
        //用redis缓存起来，减少数据库的压力
       // SecKill sk = seckillDao.queryById(seckillId);
        SecKill sk = redisDao.getSeckill(seckillId);
        if(sk == null){
            sk = seckillDao.queryById(seckillId);
            if(sk == null){
                return new Exposer(false,seckillId);
            }else{
                redisDao.putSeckill(sk);
            }
        }
        long nowTime = System.currentTimeMillis();
        long sTime = sk.getStartTime().getTime();
        long eTime = sk.getEndTime().getTime();
        if(sTime>nowTime||eTime<nowTime){
            return new Exposer(false,nowTime,sTime,eTime,seckillId);
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true,md5,seckillId);


    }


    private String getMD5(long seckillId){
        String base = seckillId + "/" +salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    /**
     * 使用注解控制事务方法的优点：1、明确编程风格 2、保证事务方法执行时间尽可能短
     * 3、不是所有方法都需要事务如只有一条修改操作，只读操作不需要事务
     * @param seckillId
     * @param userPhone
     * @param md5
     * @return
     * @throws SecKillException
     * @throws RepeatKillException
     * @throws SecKillCloseException
     */
    @Transactional
    public SecKillExecution executeSecKill(@PathVariable("seckillId")long seckillId,
                                           @CookieValue(value = "userPhone",required = false)long userPhone,
                                           @PathVariable("md5")String md5) throws SecKillException, RepeatKillException, SecKillCloseException {
        if(md5 == null||!md5.equals(getMD5(seckillId)))
        {
            throw new SecKillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();
        try {
            //插入购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                //重复秒杀
                throw new RepeatKillException("seckill repeat");
            } else {

                //减库存
                int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
                if(updateCount<=0){
                    //没有更新到记录，秒杀结束
                    throw new SecKillCloseException("seckill is closed");
                }
                //秒杀成功
                SuceessKilled sk = successKilledDao.queryByIdWithSecKill(seckillId, userPhone);
                return new SecKillExecution(seckillId, SecKillStateEnum.SUCCESS, sk);
            }

        }catch(SecKillCloseException e){
            throw e;
        }catch(RepeatKillException e){
            throw e;
        }
        catch (Exception e) {
            logger.error(e.getMessage(),e);
            throw new SecKillException("seckill inner error:"+e.getMessage());
        }

    }

    public SecKillExecution executeSecKillProcedure(long seckillId, long userPhone, String md5) throws SecKillException, RepeatKillException, SecKillCloseException {
        if(md5 == null||!md5.equals(getMD5(seckillId)))
        {
            return new SecKillExecution(seckillId,SecKillStateEnum.DATA_REWRITE);
        }
        Map<String,Object> map = new HashMap<String, Object>();
        Date killTime = new Date();
        map.put("killTime",killTime);
        map.put("seckillId",seckillId);
        map.put("phone",userPhone);
        map.put("r_result",null);
        //执行存储过程,result被复制
        try {
            seckillDao.killByProcedure(map);
            //获取result
            int result = MapUtils.getInteger(map,"r_result",-2);
            if(result == 1){
                SuceessKilled sk =successKilledDao.queryByIdWithSecKill(seckillId,userPhone);
                return new SecKillExecution(seckillId,SecKillStateEnum.SUCCESS,sk);
            }else{
                return new SecKillExecution(seckillId,SecKillStateEnum.stateOf(result));
            }
        }catch (Exception e){
            logger.error(e.getMessage() , e);
            return new SecKillExecution(seckillId,SecKillStateEnum.INNER_ERROR);
        }
    }


}
