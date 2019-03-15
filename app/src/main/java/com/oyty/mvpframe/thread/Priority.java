package com.oyty.mvpframe.thread;

/**
 * Created by oyty on 2019/3/15.
 */
public enum Priority {

    /**
     * Lowest priority level. Used for prefetches of data.
     */
    LOW,

    /**
     * Medium priority level. Used for warming for data that might soon get visible.
     */
    MEDIUM,

    /**
     * Highest priority level. Used for data that are currently visible.
     */
    HIGH,

    /**
     * Highest priority level. Used for data that required instantly(mainly for emergency)
     */
    IMMEDIATE;
}
