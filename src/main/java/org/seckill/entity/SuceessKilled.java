package org.seckill.entity;

import java.util.Date;

/**
 * Created by tangke on 2016/6/13.
 */
public class SuceessKilled {

    private  long seckillId;

    private long userPhone;

    private short state;

    private Date createTime;

    public SecKill getSeckill() {
        return seckill;
    }

    public void setSeckill(SecKill seckill) {
        this.seckill = seckill;
    }

    private SecKill seckill;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SuceessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                '}';
    }
}
