package com.plv.thirdpart.litepal.tablemanager;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import com.plv.thirdpart.litepal.LitePalApplication;
import com.plv.thirdpart.litepal.parser.LitePalAttr;
import com.umeng.commonsdk.framework.UMModuleRegister;
import java.io.File;

/* loaded from: classes5.dex */
public class Connector {
    private static LitePalOpenHelper mLitePalHelper;

    private static LitePalOpenHelper buildConnection() {
        LitePalAttr litePalAttr = LitePalAttr.getInstance();
        litePalAttr.checkSelfValid();
        if (mLitePalHelper == null) {
            String dbName = litePalAttr.getDbName();
            if ("external".equalsIgnoreCase(litePalAttr.getStorage())) {
                dbName = LitePalApplication.getContext().getExternalFilesDir("") + "/databases/" + dbName;
            } else if (!UMModuleRegister.INNER.equalsIgnoreCase(litePalAttr.getStorage()) && !TextUtils.isEmpty(litePalAttr.getStorage())) {
                String strReplace = (Environment.getExternalStorageDirectory().getPath() + "/" + litePalAttr.getStorage()).replace("//", "/");
                File file = new File(strReplace);
                if (!file.exists()) {
                    file.mkdirs();
                }
                dbName = strReplace + "/" + dbName;
            }
            mLitePalHelper = new LitePalOpenHelper(dbName, litePalAttr.getVersion());
        }
        return mLitePalHelper;
    }

    public static void clearLitePalOpenHelperInstance() {
        LitePalOpenHelper litePalOpenHelper = mLitePalHelper;
        if (litePalOpenHelper != null) {
            litePalOpenHelper.getWritableDatabase().close();
            mLitePalHelper = null;
        }
    }

    public static SQLiteDatabase getDatabase() {
        return getWritableDatabase();
    }

    public static synchronized SQLiteDatabase getWritableDatabase() {
        return buildConnection().getWritableDatabase();
    }
}
