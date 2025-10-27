package cn.hutool.core.lang;

import cn.hutool.core.exceptions.UtilException;
import cn.hutool.core.text.CharSequenceUtil;
import java.lang.management.ManagementFactory;

/* loaded from: classes.dex */
public enum Pid {
    INSTANCE;

    private final int pid = getPid();

    Pid() {
    }

    private static int getPid() throws UtilException {
        String name = ManagementFactory.getRuntimeMXBean().getName();
        if (CharSequenceUtil.isBlank(name)) {
            throw new UtilException("Process name is blank!");
        }
        int iIndexOf = name.indexOf(64);
        return iIndexOf > 0 ? Integer.parseInt(name.substring(0, iIndexOf)) : name.hashCode();
    }

    public int get() {
        return this.pid;
    }
}
