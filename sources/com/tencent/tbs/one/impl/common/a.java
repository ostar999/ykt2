package com.tencent.tbs.one.impl.common;

import android.content.Context;
import android.os.Bundle;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneComponent;
import com.tencent.tbs.one.TBSOneConfigurationKeys;
import com.tencent.tbs.one.TBSOneException;
import com.tencent.tbs.one.TBSOneManager;
import com.tencent.tbs.one.optional.TBSOneDebugPlugin;
import com.tencent.tbs.one.optional.TBSOneRuntimeExtension;
import java.io.File;

/* loaded from: classes6.dex */
public final class a {
    public static TBSOneRuntimeExtension a(Context context, String str) {
        TBSOneComponent loadedComponent = TBSOneManager.getInstance(context, str).getLoadedComponent("extension");
        if (loadedComponent == null || !(loadedComponent.getEntryObject() instanceof TBSOneRuntimeExtension)) {
            return null;
        }
        return (TBSOneRuntimeExtension) loadedComponent.getEntryObject();
    }

    public static TBSOneRuntimeExtension a(Context context, String str, boolean z2) throws TBSOneException {
        TBSOneComponent tBSOneComponentLoadComponentSync;
        TBSOneManager tBSOneManager = TBSOneManager.getInstance(context, str);
        TBSOneComponent loadedComponent = tBSOneManager.getLoadedComponent("extension");
        if (loadedComponent == null) {
            try {
                if (z2) {
                    File fileC = f.c(f.a(context.getDir("tbs", 0), str));
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("deps", d.a(fileC));
                    bundle.putInt("time_out", 30000);
                    tBSOneComponentLoadComponentSync = TBSOneManager.getInstance(context, str).loadComponentSync("extension", bundle, 30000L);
                } else {
                    tBSOneComponentLoadComponentSync = tBSOneManager.loadComponentSync("extension", 30000L);
                }
                loadedComponent = tBSOneComponentLoadComponentSync;
            } catch (TBSOneException e2) {
                throw new TBSOneException(503, "Failed to load runtime extension, error: [" + e2.getErrorCode() + "] " + e2.getMessage(), e2.getCause());
            }
        }
        if (loadedComponent == null) {
            throw new TBSOneException(509, "Failed to load runtime extension, unexpected nullpointer return");
        }
        if (loadedComponent.getEntryObject() instanceof TBSOneRuntimeExtension) {
            return (TBSOneRuntimeExtension) loadedComponent.getEntryObject();
        }
        throw new TBSOneException(504, "Failed to cast the entry object of runtime extension");
    }

    public static void a(Context context, final TBSOneCallback<TBSOneDebugPlugin> tBSOneCallback) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_FLOW_CONTROL_SUGGESTION, true);
        bundle.putBoolean(TBSOneConfigurationKeys.IGNORE_WIFI_STATE, true);
        TBSOneManager.getInstance(context, "debug").loadComponentAsync("debugger", bundle, new TBSOneCallback<TBSOneComponent>() { // from class: com.tencent.tbs.one.impl.common.a.1
            @Override // com.tencent.tbs.one.TBSOneCallback
            public final /* synthetic */ void onCompleted(TBSOneComponent tBSOneComponent) {
                Object entryObject = tBSOneComponent.getEntryObject();
                if (!(entryObject instanceof TBSOneDebugPlugin)) {
                    tBSOneCallback.onError(505, "Failed to cast the entry object of debug plugin");
                } else {
                    tBSOneCallback.onCompleted((TBSOneDebugPlugin) entryObject);
                }
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onError(int i2, String str) {
                super.onError(i2, str);
                com.tencent.tbs.one.impl.a.g.c("Failed to load debug plugin, code: " + i2 + ", detail: " + str, new Object[0]);
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onProgressChanged(int i2, int i3) {
                super.onProgressChanged(i2, i3);
                com.tencent.tbs.one.impl.a.g.c("Loading debug plugin, " + i2 + " -> " + i3, new Object[0]);
            }
        });
    }
}
