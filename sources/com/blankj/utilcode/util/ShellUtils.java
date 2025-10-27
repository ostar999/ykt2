package com.blankj.utilcode.util;

import androidx.annotation.NonNull;
import com.blankj.utilcode.util.Utils;
import java.util.List;

/* loaded from: classes2.dex */
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

        public String toString() {
            return "result: " + this.result + "\nsuccessMsg: " + this.successMsg + "\nerrorMsg: " + this.errorMsg;
        }
    }

    private ShellUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    public static CommandResult execCmd(String str, boolean z2) {
        return execCmd(new String[]{str}, z2, true);
    }

    public static Utils.Task<CommandResult> execCmdAsync(String str, boolean z2, Utils.Consumer<CommandResult> consumer) {
        return execCmdAsync(new String[]{str}, z2, true, consumer);
    }

    public static CommandResult execCmd(String str, List<String> list, boolean z2) {
        return execCmd(new String[]{str}, list == null ? null : (String[]) list.toArray(new String[0]), z2, true);
    }

    public static Utils.Task<CommandResult> execCmdAsync(List<String> list, boolean z2, Utils.Consumer<CommandResult> consumer) {
        return execCmdAsync(list == null ? null : (String[]) list.toArray(new String[0]), z2, true, consumer);
    }

    public static Utils.Task<CommandResult> execCmdAsync(String[] strArr, boolean z2, Utils.Consumer<CommandResult> consumer) {
        return execCmdAsync(strArr, z2, true, consumer);
    }

    public static CommandResult execCmd(List<String> list, boolean z2) {
        return execCmd(list == null ? null : (String[]) list.toArray(new String[0]), z2, true);
    }

    public static Utils.Task<CommandResult> execCmdAsync(String str, boolean z2, boolean z3, Utils.Consumer<CommandResult> consumer) {
        return execCmdAsync(new String[]{str}, z2, z3, consumer);
    }

    public static CommandResult execCmd(List<String> list, List<String> list2, boolean z2) {
        return execCmd(list == null ? null : (String[]) list.toArray(new String[0]), list2 != null ? (String[]) list2.toArray(new String[0]) : null, z2, true);
    }

    public static Utils.Task<CommandResult> execCmdAsync(List<String> list, boolean z2, boolean z3, Utils.Consumer<CommandResult> consumer) {
        return execCmdAsync(list == null ? null : (String[]) list.toArray(new String[0]), z2, z3, consumer);
    }

    public static Utils.Task<CommandResult> execCmdAsync(final String[] strArr, final boolean z2, final boolean z3, @NonNull Utils.Consumer<CommandResult> consumer) {
        return UtilsBridge.doAsync(new Utils.Task<CommandResult>(consumer) { // from class: com.blankj.utilcode.util.ShellUtils.1
            @Override // com.blankj.utilcode.util.ThreadUtils.Task
            public CommandResult doInBackground() {
                return ShellUtils.execCmd(strArr, z2, z3);
            }
        });
    }

    public static CommandResult execCmd(String[] strArr, boolean z2) {
        return execCmd(strArr, z2, true);
    }

    public static CommandResult execCmd(String str, boolean z2, boolean z3) {
        return execCmd(new String[]{str}, z2, z3);
    }

    public static CommandResult execCmd(String str, List<String> list, boolean z2, boolean z3) {
        return execCmd(new String[]{str}, list == null ? null : (String[]) list.toArray(new String[0]), z2, z3);
    }

    public static CommandResult execCmd(String str, String[] strArr, boolean z2, boolean z3) {
        return execCmd(new String[]{str}, strArr, z2, z3);
    }

    public static CommandResult execCmd(List<String> list, boolean z2, boolean z3) {
        return execCmd(list == null ? null : (String[]) list.toArray(new String[0]), z2, z3);
    }

    public static CommandResult execCmd(String[] strArr, boolean z2, boolean z3) {
        return execCmd(strArr, (String[]) null, z2, z3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:106:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0171  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0153 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x012d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0167 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x0123 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x015d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0119 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:173:? A[SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r10v11 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v16 */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Type inference failed for: r11v11 */
    /* JADX WARN: Type inference failed for: r11v12 */
    /* JADX WARN: Type inference failed for: r11v3, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r11v9, types: [java.lang.StringBuilder] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.blankj.utilcode.util.ShellUtils.CommandResult execCmd(java.lang.String[] r8, java.lang.String[] r9, boolean r10, boolean r11) throws java.lang.Throwable {
        /*
            Method dump skipped, instructions count: 379
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.blankj.utilcode.util.ShellUtils.execCmd(java.lang.String[], java.lang.String[], boolean, boolean):com.blankj.utilcode.util.ShellUtils$CommandResult");
    }
}
