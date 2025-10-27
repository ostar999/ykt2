package com.plv.thirdpart.blankj.utilcode.util;

import java.util.List;

/* loaded from: classes5.dex */
public final class ShellUtils {
    private static final String LINE_SEP = System.getProperty("line.separator");

    public static class CommandResult {
        public String errorMsg;
        public int result;
        public String successMsg;

        public CommandResult(int i2, String str, String str2) {
            this.result = i2;
            this.successMsg = str;
            this.errorMsg = str2;
        }
    }

    private ShellUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static CommandResult execCmd(String str, boolean z2) {
        return execCmd(new String[]{str}, z2, true);
    }

    public static CommandResult execCmd(List<String> list, boolean z2) {
        return execCmd(list == null ? null : (String[]) list.toArray(new String[0]), z2, true);
    }

    public static CommandResult execCmd(String[] strArr, boolean z2) {
        return execCmd(strArr, z2, true);
    }

    public static CommandResult execCmd(String str, boolean z2, boolean z3) {
        return execCmd(new String[]{str}, z2, z3);
    }

    public static CommandResult execCmd(List<String> list, boolean z2, boolean z3) {
        return execCmd(list == null ? null : (String[]) list.toArray(new String[0]), z2, z3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0121  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0140  */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v6 */
    /* JADX WARN: Type inference failed for: r0v7, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r0v8 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r13v11, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r15v4, types: [java.io.Closeable[]] */
    /* JADX WARN: Type inference failed for: r7v2 */
    /* JADX WARN: Type inference failed for: r7v3 */
    /* JADX WARN: Type inference failed for: r7v5, types: [java.io.DataOutputStream, java.io.OutputStream] */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.plv.thirdpart.blankj.utilcode.util.ShellUtils.CommandResult execCmd(java.lang.String[] r13, boolean r14, boolean r15) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 330
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.plv.thirdpart.blankj.utilcode.util.ShellUtils.execCmd(java.lang.String[], boolean, boolean):com.plv.thirdpart.blankj.utilcode.util.ShellUtils$CommandResult");
    }
}
