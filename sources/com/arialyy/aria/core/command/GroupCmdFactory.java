package com.arialyy.aria.core.command;

/* loaded from: classes2.dex */
public class GroupCmdFactory {
    private static volatile GroupCmdFactory INSTANCE = null;
    public static final int SUB_TASK_START = 161;
    public static final int SUB_TASK_STOP = 162;

    private GroupCmdFactory() {
    }

    public static GroupCmdFactory getInstance() {
        if (INSTANCE == null) {
            synchronized (GroupCmdFactory.class) {
                INSTANCE = new GroupCmdFactory();
            }
        }
        return INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0018  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public com.arialyy.aria.core.command.AbsGroupCmd createCmd(com.arialyy.aria.core.download.AbsGroupTaskWrapper r2, int r3, java.lang.String r4) {
        /*
            r1 = this;
            r0 = 161(0xa1, float:2.26E-43)
            if (r3 == r0) goto L10
            r0 = 162(0xa2, float:2.27E-43)
            if (r3 == r0) goto La
            r2 = 0
            goto L16
        La:
            com.arialyy.aria.core.command.DGSubStopCmd r3 = new com.arialyy.aria.core.command.DGSubStopCmd
            r3.<init>(r2)
            goto L15
        L10:
            com.arialyy.aria.core.command.DGSubStartCmd r3 = new com.arialyy.aria.core.command.DGSubStartCmd
            r3.<init>(r2)
        L15:
            r2 = r3
        L16:
            if (r2 == 0) goto L1a
            r2.childUrl = r4
        L1a:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arialyy.aria.core.command.GroupCmdFactory.createCmd(com.arialyy.aria.core.download.AbsGroupTaskWrapper, int, java.lang.String):com.arialyy.aria.core.command.AbsGroupCmd");
    }
}
