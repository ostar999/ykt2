package com.arialyy.aria.core.loader;

import com.arialyy.aria.core.common.AbsEntity;
import com.arialyy.aria.core.common.CompleteInfo;
import com.arialyy.aria.exception.AriaException;

/* loaded from: classes2.dex */
public interface IInfoTask extends ILoaderComponent {

    public interface Callback {
        void onFail(AbsEntity absEntity, AriaException ariaException, boolean z2);

        void onSucceed(String str, CompleteInfo completeInfo);
    }

    void run();

    void setCallback(Callback callback);
}
