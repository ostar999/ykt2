package com.arialyy.aria.core.config;

import android.text.TextUtils;
import com.arialyy.aria.core.AriaConfig;
import com.arialyy.aria.util.ALog;
import com.arialyy.aria.util.FileUtil;
import java.io.Serializable;

/* loaded from: classes2.dex */
abstract class BaseConfig implements Serializable {
    private static final String TAG = "BaseConfig";
    static final int TYPE_APP = 3;
    static final int TYPE_DGROUP = 4;
    static final int TYPE_DOWNLOAD = 1;
    static final int TYPE_UPLOAD = 2;

    abstract int getType();

    void save() throws Throwable {
        String path = AriaConfig.getInstance().getAPP().getFilesDir().getPath();
        int type = getType();
        String str = type != 1 ? type != 2 ? type != 3 ? type != 4 ? null : "/Aria/AriaDGroup.cfg" : "/Aria/AriaApp.cfg" : "/Aria/AriaUpload.cfg" : "/Aria/AriaDownload.cfg";
        if (TextUtils.isEmpty(str)) {
            ALog.e(TAG, String.format("保存配置失败，配置类型：%s，原因：路径错误", Integer.valueOf(getType())));
            return;
        }
        String str2 = String.format("%s%s", path, str);
        FileUtil.deleteFile(str2);
        FileUtil.writeObjToFile(str2, this);
    }
}
