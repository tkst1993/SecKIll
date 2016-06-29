package org.seckill.enums;

/**
 * 使用枚举表示常量数据字典
 * Created by tangke on 2016/6/29.
 */
public enum  SecKillStateEnum {
    SUCCESS(1,"秒杀成功"),
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");

    private int state;

    private String stateInfo;

    public String getStateInfo() {
        return stateInfo;
    }

    public int getState() {
        return state;
    }

    SecKillStateEnum(int state, String stateInfo) {
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public static SecKillStateEnum stateOf(int index){
        for(SecKillStateEnum state : values()){
            if(state.getState() == index){
                return state;
            }
        }
        return null;
    }
}
