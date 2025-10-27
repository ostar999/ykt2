package com.plv.thirdpart.litepal.tablemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.plv.thirdpart.litepal.LitePalApplication;
import com.plv.thirdpart.litepal.Operator;
import com.plv.thirdpart.litepal.parser.LitePalAttr;
import com.plv.thirdpart.litepal.tablemanager.callback.DatabaseListener;
import com.plv.thirdpart.litepal.util.SharedUtil;

/* loaded from: classes5.dex */
class LitePalOpenHelper extends SQLiteOpenHelper {
    public static final String TAG = "LitePalHelper";

    public LitePalOpenHelper(Context context, String str, SQLiteDatabase.CursorFactory cursorFactory, int i2) {
        super(context, str, cursorFactory, i2);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Generator.create(sQLiteDatabase);
        final DatabaseListener dBListener = Operator.getDBListener();
        if (dBListener != null) {
            LitePalApplication.sHandler.post(new Runnable() { // from class: com.plv.thirdpart.litepal.tablemanager.LitePalOpenHelper.1
                @Override // java.lang.Runnable
                public void run() {
                    dBListener.onCreate();
                }
            });
        }
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, final int i2, final int i3) {
        Generator.upgrade(sQLiteDatabase);
        SharedUtil.updateVersion(LitePalAttr.getInstance().getExtraKeyName(), i3);
        final DatabaseListener dBListener = Operator.getDBListener();
        if (dBListener != null) {
            LitePalApplication.sHandler.post(new Runnable() { // from class: com.plv.thirdpart.litepal.tablemanager.LitePalOpenHelper.2
                @Override // java.lang.Runnable
                public void run() {
                    dBListener.onUpgrade(i2, i3);
                }
            });
        }
    }

    public LitePalOpenHelper(String str, int i2) {
        this(LitePalApplication.getContext(), str, null, i2);
    }
}
