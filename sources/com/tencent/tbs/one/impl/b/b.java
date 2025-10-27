package com.tencent.tbs.one.impl.b;

import android.content.Context;
import android.view.View;
import com.tencent.tbs.one.TBSOneCallback;
import com.tencent.tbs.one.TBSOneDebugger;
import com.tencent.tbs.one.optional.TBSOneDebugPlugin;
import java.util.Map;

/* loaded from: classes6.dex */
public final class b implements TBSOneDebugger {

    /* renamed from: a, reason: collision with root package name */
    public final String f21793a;

    public b(String str) {
        this.f21793a = str;
    }

    @Override // com.tencent.tbs.one.TBSOneDebugger
    public final View createPanelView(Context context) {
        return new a(context, this.f21793a);
    }

    @Override // com.tencent.tbs.one.TBSOneDebugger
    public final void executeCommand(final Context context, final String str, final Map<String, Object> map, final TBSOneCallback<Void> tBSOneCallback) {
        com.tencent.tbs.one.impl.common.a.a(context, new TBSOneCallback<TBSOneDebugPlugin>() { // from class: com.tencent.tbs.one.impl.b.b.1
            @Override // com.tencent.tbs.one.TBSOneCallback
            public final /* synthetic */ void onCompleted(TBSOneDebugPlugin tBSOneDebugPlugin) {
                try {
                    tBSOneDebugPlugin.executeCommand(context, b.this.f21793a, str, map, tBSOneCallback);
                } catch (UnsupportedOperationException unused) {
                    TBSOneCallback tBSOneCallback2 = tBSOneCallback;
                    if (tBSOneCallback2 != null) {
                        tBSOneCallback2.onError(102, "Unimplemented method");
                    }
                }
            }

            @Override // com.tencent.tbs.one.TBSOneCallback
            public final void onError(int i2, String str2) {
                TBSOneCallback tBSOneCallback2 = tBSOneCallback;
                if (tBSOneCallback2 != null) {
                    tBSOneCallback2.onError(i2, str2);
                }
            }
        });
    }
}
