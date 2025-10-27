package androidx.room.util;

import android.database.Cursor;
import androidx.annotation.RestrictTo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import cn.hutool.core.text.CharPool;
import java.util.HashSet;
import java.util.Set;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public final class FtsTableInfo {
    private static final String[] FTS_OPTIONS = {"tokenize=", "compress=", "content=", "languageid=", "matchinfo=", "notindexed=", "order=", "prefix=", "uncompress="};
    public final Set<String> columns;
    public final String name;
    public final Set<String> options;

    public FtsTableInfo(String str, Set<String> set, Set<String> set2) {
        this.name = str;
        this.columns = set;
        this.options = set2;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x0087  */
    @androidx.annotation.VisibleForTesting
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.util.Set<java.lang.String> parseOptions(java.lang.String r8) {
        /*
            boolean r0 = r8.isEmpty()
            if (r0 == 0) goto Lc
            java.util.HashSet r8 = new java.util.HashSet
            r8.<init>()
            return r8
        Lc:
            r0 = 40
            int r0 = r8.indexOf(r0)
            int r0 = r0 + 1
            r1 = 41
            int r1 = r8.lastIndexOf(r1)
            java.lang.String r8 = r8.substring(r0, r1)
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.ArrayDeque r1 = new java.util.ArrayDeque
            r1.<init>()
            r2 = 0
            r3 = -1
            r4 = r2
        L2b:
            int r5 = r8.length()
            if (r4 >= r5) goto La7
            char r5 = r8.charAt(r4)
            r6 = 34
            if (r5 == r6) goto L87
            r6 = 39
            if (r5 == r6) goto L87
            r6 = 44
            if (r5 == r6) goto L72
            r6 = 91
            if (r5 == r6) goto L64
            r7 = 93
            if (r5 == r7) goto L4e
            r6 = 96
            if (r5 == r6) goto L87
            goto La4
        L4e:
            boolean r5 = r1.isEmpty()
            if (r5 != 0) goto La4
            java.lang.Object r5 = r1.peek()
            java.lang.Character r5 = (java.lang.Character) r5
            char r5 = r5.charValue()
            if (r5 != r6) goto La4
            r1.pop()
            goto La4
        L64:
            boolean r6 = r1.isEmpty()
            if (r6 == 0) goto La4
            java.lang.Character r5 = java.lang.Character.valueOf(r5)
            r1.push(r5)
            goto La4
        L72:
            boolean r5 = r1.isEmpty()
            if (r5 == 0) goto La4
            int r3 = r3 + 1
            java.lang.String r3 = r8.substring(r3, r4)
            java.lang.String r3 = r3.trim()
            r0.add(r3)
            r3 = r4
            goto La4
        L87:
            boolean r6 = r1.isEmpty()
            if (r6 == 0) goto L95
            java.lang.Character r5 = java.lang.Character.valueOf(r5)
            r1.push(r5)
            goto La4
        L95:
            java.lang.Object r6 = r1.peek()
            java.lang.Character r6 = (java.lang.Character) r6
            char r6 = r6.charValue()
            if (r6 != r5) goto La4
            r1.pop()
        La4:
            int r4 = r4 + 1
            goto L2b
        La7:
            int r3 = r3 + 1
            java.lang.String r8 = r8.substring(r3)
            java.lang.String r8 = r8.trim()
            r0.add(r8)
            java.util.HashSet r8 = new java.util.HashSet
            r8.<init>()
            java.util.Iterator r0 = r0.iterator()
        Lbd:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto Ldd
            java.lang.Object r1 = r0.next()
            java.lang.String r1 = (java.lang.String) r1
            java.lang.String[] r3 = androidx.room.util.FtsTableInfo.FTS_OPTIONS
            int r4 = r3.length
            r5 = r2
        Lcd:
            if (r5 >= r4) goto Lbd
            r6 = r3[r5]
            boolean r6 = r1.startsWith(r6)
            if (r6 == 0) goto Lda
            r8.add(r1)
        Lda:
            int r5 = r5 + 1
            goto Lcd
        Ldd:
            return r8
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.room.util.FtsTableInfo.parseOptions(java.lang.String):java.util.Set");
    }

    public static FtsTableInfo read(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
        return new FtsTableInfo(str, readColumns(supportSQLiteDatabase, str), readOptions(supportSQLiteDatabase, str));
    }

    private static Set<String> readColumns(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
        Cursor cursorQuery = supportSQLiteDatabase.query("PRAGMA table_info(`" + str + "`)");
        HashSet hashSet = new HashSet();
        try {
            if (cursorQuery.getColumnCount() > 0) {
                int columnIndex = cursorQuery.getColumnIndex("name");
                while (cursorQuery.moveToNext()) {
                    hashSet.add(cursorQuery.getString(columnIndex));
                }
            }
            return hashSet;
        } finally {
            cursorQuery.close();
        }
    }

    private static Set<String> readOptions(SupportSQLiteDatabase supportSQLiteDatabase, String str) {
        Cursor cursorQuery = supportSQLiteDatabase.query("SELECT * FROM sqlite_master WHERE `name` = '" + str + "'");
        try {
            String string = cursorQuery.moveToFirst() ? cursorQuery.getString(cursorQuery.getColumnIndexOrThrow("sql")) : "";
            cursorQuery.close();
            return parseOptions(string);
        } catch (Throwable th) {
            cursorQuery.close();
            throw th;
        }
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof FtsTableInfo)) {
            return false;
        }
        FtsTableInfo ftsTableInfo = (FtsTableInfo) obj;
        String str = this.name;
        if (str == null ? ftsTableInfo.name != null : !str.equals(ftsTableInfo.name)) {
            return false;
        }
        Set<String> set = this.columns;
        if (set == null ? ftsTableInfo.columns != null : !set.equals(ftsTableInfo.columns)) {
            return false;
        }
        Set<String> set2 = this.options;
        Set<String> set3 = ftsTableInfo.options;
        return set2 != null ? set2.equals(set3) : set3 == null;
    }

    public int hashCode() {
        String str = this.name;
        int iHashCode = (str != null ? str.hashCode() : 0) * 31;
        Set<String> set = this.columns;
        int iHashCode2 = (iHashCode + (set != null ? set.hashCode() : 0)) * 31;
        Set<String> set2 = this.options;
        return iHashCode2 + (set2 != null ? set2.hashCode() : 0);
    }

    public String toString() {
        return "FtsTableInfo{name='" + this.name + CharPool.SINGLE_QUOTE + ", columns=" + this.columns + ", options=" + this.options + '}';
    }

    public FtsTableInfo(String str, Set<String> set, String str2) {
        this.name = str;
        this.columns = set;
        this.options = parseOptions(str2);
    }
}
