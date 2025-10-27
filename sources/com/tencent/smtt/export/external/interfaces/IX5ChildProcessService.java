package com.tencent.smtt.export.external.interfaces;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/* loaded from: classes6.dex */
public interface IX5ChildProcessService {
    public static final String ENTRY_CLASS_NAME_KEY = "com.tencent.smtt.multiprocess.ENTRY_CLASS_NAME";
    public static final String ENTRY_DEX_FILE_KEY = "com.tencent.smtt.multiprocess.ENTRY_DEX_FILE";
    public static final String ERROR_CALLBACK_KEY = "com.tencent.smtt.multiprocess.ERROR_CALLBACK";
    public static final String INSTALLATION_DIRECTORY_KEY = "com.tencent.smtt.multiprocess.INSTALLATION_DIRECTORY";

    IBinder onBind(Service service, Intent intent);

    void onCreate(Service service, Intent intent);

    void onDestroy(Service service);
}
