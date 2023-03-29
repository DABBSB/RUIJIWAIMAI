package org.DABB.commons;

public class BaseContext {
    private static final ThreadLocal<Long> LONG_THREAD_LOCAL = new ThreadLocal<>();

    public static void setid(Long id) {
        LONG_THREAD_LOCAL.set(id);
    }

    public static Long getid() {
        return LONG_THREAD_LOCAL.get();
    }
}
