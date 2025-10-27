package com.plv.foundationsdk.net;

import com.plv.foundationsdk.log.PLVCommonLog;

/* loaded from: classes4.dex */
public abstract class PLVrResponseCallback<Bean> {
    private static final String TAG = "PLVrResponseCallback";
    protected PLVResponseBean<Bean> responseBean;

    public PLVResponseBean<Bean> getResponseBean() {
        return this.responseBean;
    }

    public void onError(Throwable th) {
        if (th == null) {
            return;
        }
        PLVCommonLog.e(TAG, "PLVrResponseCallback onError:" + th.getMessage());
    }

    public void onFailure(PLVResponseBean<Bean> pLVResponseBean) {
        PLVCommonLog.d(TAG, "PLVrResponseCallback onFailure：" + pLVResponseBean.message);
    }

    public abstract void onFinish();

    public abstract void onSuccess(Bean bean);

    public void setResponseBean(PLVResponseBean<Bean> pLVResponseBean) {
        this.responseBean = pLVResponseBean;
    }
}
