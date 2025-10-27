package androidx.room.util;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.RestrictTo;
import androidx.annotation.VisibleForTesting;
import cn.hutool.core.text.StrPool;
import java.util.Arrays;

@RestrictTo({RestrictTo.Scope.LIBRARY_GROUP_PREFIX})
/* loaded from: classes.dex */
public class CursorUtil {
    private CursorUtil() {
    }

    @NonNull
    public static Cursor copyAndClose(@NonNull Cursor cursor) {
        try {
            MatrixCursor matrixCursor = new MatrixCursor(cursor.getColumnNames(), cursor.getCount());
            while (cursor.moveToNext()) {
                Object[] objArr = new Object[cursor.getColumnCount()];
                for (int i2 = 0; i2 < cursor.getColumnCount(); i2++) {
                    int type = cursor.getType(i2);
                    if (type == 0) {
                        objArr[i2] = null;
                    } else if (type == 1) {
                        objArr[i2] = Long.valueOf(cursor.getLong(i2));
                    } else if (type == 2) {
                        objArr[i2] = Double.valueOf(cursor.getDouble(i2));
                    } else if (type == 3) {
                        objArr[i2] = cursor.getString(i2);
                    } else {
                        if (type != 4) {
                            throw new IllegalStateException();
                        }
                        objArr[i2] = cursor.getBlob(i2);
                    }
                }
                matrixCursor.addRow(objArr);
            }
            return matrixCursor;
        } finally {
            cursor.close();
        }
    }

    private static int findColumnIndexBySuffix(@NonNull Cursor cursor, @NonNull String str) {
        if (Build.VERSION.SDK_INT <= 25 && str.length() != 0) {
            return findColumnIndexBySuffix(cursor.getColumnNames(), str);
        }
        return -1;
    }

    public static int getColumnIndex(@NonNull Cursor cursor, @NonNull String str) {
        int columnIndex = cursor.getColumnIndex(str);
        if (columnIndex >= 0) {
            return columnIndex;
        }
        int columnIndex2 = cursor.getColumnIndex("`" + str + "`");
        return columnIndex2 >= 0 ? columnIndex2 : findColumnIndexBySuffix(cursor, str);
    }

    public static int getColumnIndexOrThrow(@NonNull Cursor cursor, @NonNull String str) {
        String string;
        int columnIndex = getColumnIndex(cursor, str);
        if (columnIndex >= 0) {
            return columnIndex;
        }
        try {
            string = Arrays.toString(cursor.getColumnNames());
        } catch (Exception e2) {
            Log.d("RoomCursorUtil", "Cannot collect column names for debug purposes", e2);
            string = "";
        }
        throw new IllegalArgumentException("column '" + str + "' does not exist. Available columns: " + string);
    }

    @VisibleForTesting(otherwise = 2)
    public static int findColumnIndexBySuffix(String[] strArr, String str) {
        String str2 = StrPool.DOT + str;
        String str3 = StrPool.DOT + str + "`";
        for (int i2 = 0; i2 < strArr.length; i2++) {
            String str4 = strArr[i2];
            if (str4.length() >= str.length() + 2) {
                if (str4.endsWith(str2)) {
                    return i2;
                }
                if (str4.charAt(0) == '`' && str4.endsWith(str3)) {
                    return i2;
                }
            }
        }
        return -1;
    }
}
