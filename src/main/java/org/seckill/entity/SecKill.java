package org.seckill.entity;

import java.util.Date;

/**
 * Created by tangke on 2016/6/13.
 */
public class SecKill {
    private long seckillId;
    private String name;
    private int number;
    private Date startTime;

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCrateTime() {
        return crateTime;
    }

    public void setCrateTime(Date crateTime) {
        this.crateTime = crateTime;
    }

    private Date endTime;
    private Date crateTime;

    @Override
    public String toString() {
        return "SecKill{" +
                "seckillId=" + seckillId +
                ", name='" + name  +
                ", number=" + number +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", crateTime=" + crateTime +
                '}';
    }
}
