package com.oyty.mvpframe.thread;

import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by oyty on 2019/3/15.
 */
public class PriorityThreadPoolExecutor extends ThreadPoolExecutor {

    public PriorityThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, ThreadFactory threadFactory) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, new PriorityBlockingQueue<Runnable>(), threadFactory);
    }

    @Override
    public Future<?> submit(Runnable task) {

        return super.submit(task);
    }

    private static final class PriorityFutureTask extends FutureTask<PriorityRunnable>
        implements Comparable<PriorityFutureTask> {

        private final PriorityRunnable priorityRunnable;

        public PriorityFutureTask(PriorityRunnable runnable) {
            super(runnable, null);
            this.priorityRunnable = runnable;
        }

        @Override
        public int compareTo(PriorityFutureTask o) {
            Priority p1 = priorityRunnable.getPriority();
            Priority p2 = o.priorityRunnable.getPriority();
            return p2.ordinal() - p1.ordinal();
        }
    }
}
