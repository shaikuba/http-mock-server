package cn.shaikuba.mock.common.thread;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class PrefixThreadFactory implements ThreadFactory {

    private final AtomicInteger threadNumber = new AtomicInteger(0);

    private final String prefixName;

    public PrefixThreadFactory(String prefixName) {
        if (prefixName == null || prefixName.isEmpty()) {
            throw new IllegalArgumentException("Prefix name of threads is required.");
        }
        this.prefixName = prefixName;
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread thread = new Thread(r);
        thread.setName(String.format("%s-%d", prefixName, threadNumber.getAndIncrement()));
        thread.setDaemon(false);
        if (thread.getPriority() != Thread.NORM_PRIORITY) {
            thread.setPriority(Thread.NORM_PRIORITY);
        }
        return thread;
    }
}
