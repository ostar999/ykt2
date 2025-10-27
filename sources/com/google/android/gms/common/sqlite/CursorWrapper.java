package com.google.android.gms.common.sqlite;

import android.database.AbstractWindowedCursor;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;
import com.google.android.gms.common.annotation.KeepForSdk;

@KeepForSdk
/* loaded from: classes3.dex */
public class CursorWrapper extends android.database.CursorWrapper implements CrossProcessCursor {
    private AbstractWindowedCursor zzez;

    @KeepForSdk
    public CursorWrapper(Cursor cursor) {
        super(cursor);
        for (int i2 = 0; i2 < 10 && (cursor instanceof android.database.CursorWrapper); i2++) {
            cursor = ((android.database.CursorWrapper) cursor).getWrappedCursor();
        }
        if (!(cursor instanceof AbstractWindowedCursor)) {
            String name = cursor.getClass().getName();
            throw new IllegalArgumentException(name.length() != 0 ? "Unknown type: ".concat(name) : new String("Unknown type: "));
        }
        this.zzez = (AbstractWindowedCursor) cursor;
    }

    @Override // android.database.CrossProcessCursor
    @KeepForSdk
    public void fillWindow(int i2, CursorWindow cursorWindow) {
        this.zzez.fillWindow(i2, cursorWindow);
    }

    @Override // android.database.CrossProcessCursor
    @KeepForSdk
    public CursorWindow getWindow() {
        return this.zzez.getWindow();
    }

    @Override // android.database.CursorWrapper
    public /* synthetic */ Cursor getWrappedCursor() {
        return this.zzez;
    }

    @Override // android.database.CrossProcessCursor
    public boolean onMove(int i2, int i3) {
        return this.zzez.onMove(i2, i3);
    }

    @KeepForSdk
    public void setWindow(CursorWindow cursorWindow) {
        this.zzez.setWindow(cursorWindow);
    }
}
