package org.seckill.dto;

import org.seckill.entity.SuceessKilled;

/**
 * 封装秒杀执行后的结果
 * Created by tangke on 2016/6/27.
 */
public class SecKillExecution {
    private long seckillId;

    //秒杀执行状态
    private int state;

    //状态表示
    private String stateInfo;

    //秒杀成功对象
    private SuceessKilled successKilled;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuceessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuceessKilled successKilled) {
        this.successKilled = successKilled;
    }

    public SecKillExecution(long seckillId, int state, String stateInfo, SuceessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.successKilled = successKilled;
    }

    public SecKillExecution(long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }
}
