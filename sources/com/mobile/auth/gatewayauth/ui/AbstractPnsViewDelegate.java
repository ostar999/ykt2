package com.mobile.auth.gatewayauth.ui;

import android.content.Context;
import android.view.View;
import com.mobile.auth.gatewayauth.ExceptionProcessor;
import com.mobile.auth.gatewayauth.annotations.AuthNumber;

@AuthNumber
/* loaded from: classes4.dex */
public abstract class AbstractPnsViewDelegate {
    private a mPnsView;

    public final View findViewById(int i2) {
        try {
            a aVar = this.mPnsView;
            if (aVar != null) {
                return aVar.a(i2);
            }
            return null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public final Context getContext() {
        try {
            a aVar = this.mPnsView;
            if (aVar != null) {
                return aVar.a();
            }
            return null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public final View getRootView() {
        try {
            a aVar = this.mPnsView;
            if (aVar != null) {
                return aVar.b();
            }
            return null;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
            return null;
        }
    }

    public abstract void onViewCreated(View view);

    public final void setPnsView(a aVar) {
        try {
            this.mPnsView = aVar;
        } catch (Throwable th) {
            ExceptionProcessor.processException(th);
        }
    }
}
