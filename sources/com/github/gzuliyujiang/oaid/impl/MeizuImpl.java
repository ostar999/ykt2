package com.github.gzuliyujiang.oaid.impl;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import com.github.gzuliyujiang.oaid.IGetter;
import com.github.gzuliyujiang.oaid.IOAID;
import com.github.gzuliyujiang.oaid.OAIDException;
import com.github.gzuliyujiang.oaid.OAIDLog;
import java.util.Objects;

/* loaded from: classes3.dex */
class MeizuImpl implements IOAID {
    private final Context context;

    public MeizuImpl(Context context) {
        this.context = context;
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public void doGet(IGetter iGetter) {
        if (this.context == null || iGetter == null) {
            return;
        }
        try {
            Cursor cursorQuery = this.context.getContentResolver().query(Uri.parse("content://com.meizu.flyme.openidsdk/"), null, null, new String[]{"oaid"}, null);
            try {
                Objects.requireNonNull(cursorQuery);
                cursorQuery.moveToFirst();
                String string = cursorQuery.getString(cursorQuery.getColumnIndex("value"));
                if (string == null || string.length() == 0) {
                    throw new OAIDException("OAID query failed");
                }
                OAIDLog.print("OAID query success: " + string);
                iGetter.onOAIDGetComplete(string);
                cursorQuery.close();
            } finally {
            }
        } catch (Exception e2) {
            OAIDLog.print(e2);
            iGetter.onOAIDGetError(e2);
        }
    }

    @Override // com.github.gzuliyujiang.oaid.IOAID
    public boolean supported() {
        Context context = this.context;
        if (context == null) {
            return false;
        }
        try {
            return context.getPackageManager().resolveContentProvider("com.meizu.flyme.openidsdk", 0) != null;
        } catch (Exception e2) {
            OAIDLog.print(e2);
            return false;
        }
    }
}
