package com.oyty.mvpframe.thread;

/**
 * Created by oyty on 2019/3/15.
 */
public class BaseExecutorSupplier {

    /**
     * Number of cores to decide the number of threads
     */
    public static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
}
