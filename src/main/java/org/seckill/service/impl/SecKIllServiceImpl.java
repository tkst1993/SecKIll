package org.seckill.service.impl;

import org.seckill.dto.Exposer;
import org.seckill.dto.SecKillExecution;
import org.seckill.entity.SecKill;
import org.seckill.exception.RepeatKillException;
import org.seckill.exception.SecKillCloseException;
import org.seckill.exception.SecKillException;
import org.seckill.service.SecKillService;

import java.util.List;

/**
 * Created by tangke on 2016/6/28.
 */
public class SecKIllServiceImpl implements SecKillService {

    public List<SecKill> getSecKillList() {
        return null;
    }

    public SecKill getById(long seckillId) {
        return null;
    }

    public Exposer exportSeckillUrl(long seckillId) {
        return null;
    }

    public SecKillExecution executeSecKill(long seckillId, long userPhone, String md5) throws SecKillException, RepeatKillException, SecKillCloseException {
        return null;
    }
}
