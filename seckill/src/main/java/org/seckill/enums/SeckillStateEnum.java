package org.seckill.enums;

/**
 * Created by 探险家never on 2017/1/5.
 */
public enum  SeckillStateEnum {
    SUCCESS(1,"秒杀成功"),//创建对象
    END(0,"秒杀结束"),
    REPEAT_KILL(-1,"重复秒杀"),
    INNER_ERROR(-2,"系统异常"),
    DATA_REWRITE(-3,"数据篡改");
    private int state;
    private String stateInfo;

    private SeckillStateEnum(int state, String stateInfo) {//私有的，不能自己构造新的，只能获取已有的
        this.state = state;
        this.stateInfo = stateInfo;
    }
    public int getState() {
        return state;
    }

    public String getStateInfo() {
        return stateInfo;
    }
    public static SeckillStateEnum stateOf(int index)
    {
        for(SeckillStateEnum state:values())//values方法API中看不到
        {
            if(state.getState()==index)
            {
                return state;
            }
        }
        return null;
    }
}
