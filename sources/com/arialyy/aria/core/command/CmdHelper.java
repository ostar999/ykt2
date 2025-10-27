package com.arialyy.aria.core.command;

import com.arialyy.aria.core.download.AbsGroupTaskWrapper;
import com.arialyy.aria.core.wrapper.AbsTaskWrapper;

/* loaded from: classes2.dex */
public class CmdHelper {
    public static <T extends AbsGroupTaskWrapper> AbsGroupCmd createGroupCmd(T t2, int i2, String str) {
        return GroupCmdFactory.getInstance().createCmd(t2, i2, str);
    }

    public static <T extends AbsTaskWrapper> AbsNormalCmd createNormalCmd(T t2, int i2, int i3) {
        return NormalCmdFactory.getInstance().createCmd((AbsTaskWrapper) t2, i2, i3);
    }
}
