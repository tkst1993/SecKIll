package org.seckill.service.impl;

import org.seckill.dao.SecKillDao;
import org.seckill.dao.SuccessKilledDao;
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
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by tangke on 2016/6/28.
 */
public class SecKIllServiceImpl implements SecKillService {
    private Logger logger= LoggerFactory.getLogger(SecKIllServiceImpl.class);

    private SecKillDao seckillDao;
    private SuccessKilledDao successKilledDao;
    //md5盐值字符串
    private final String salt = "erwqjinwewrjinvn324332#$@@%@#";

    public List<SecKill> getSecKillList() {
        return seckillDao.queryAll(0,4);
    }

    public SecKill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        SecKill sk = seckillDao.queryById(seckillId);
        if(sk == null){
            return new Exposer(false,seckillId);
        }else{
            long nowTime = System.currentTimeMillis();
            long sTime = sk.getStartTime().getTime();
            long eTime = sk.getEndTime().getTime();
            if(sTime<nowTime&&eTime>nowTime){

                String md5 = getMD5(seckillId);
                return new Exposer(true,md5,seckillId);
            }else{
                return new Exposer(false,nowTime,sTime,eTime,seckillId);
            }
        }

    }


    private String getMD5(long seckillId){
        String base = seckillId + "/" +salt;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public SecKillExecution executeSecKill(long seckillId, long userPhone, String md5) throws SecKillException, RepeatKillException, SecKillCloseException {
        if(md5 == null||!md5.equals(getMD5(seckillId)))
        {
            throw new SecKillException("seckill data rewrite");
        }
        //执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();
        try {
            //减库存
            int updateCount = seckillDao.reduceNumber(seckillId,nowTime);
            if(updateCount<=0){
                //没有更新到记录，秒杀结束
                throw new SecKillCloseException("seckill is closed");
            }else {
                //插入购买行为
                int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
                if (insertCount <= 0) {
                    //重复秒杀
                    throw new RepeatKillException("seckill repeat");
                } else {
                    //秒杀成功
                    SuceessKilled sk = successKilledDao.queryByIdWithSecKill(seckillId, userPhone);
                    return new SecKillExecution(seckillId, SecKillStateEnum.SUCCESS, sk);
                }
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
}
