package com.plv.business.api.common.ppt;

import androidx.annotation.NonNull;
import com.plv.business.api.common.ppt.vo.PLVPPTLocalCacheVO;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes4.dex */
public interface IPLVPPTView {

    @Retention(RetentionPolicy.SOURCE)
    public @interface Visibility {
    }

    void onLoadLocalPpt(@NonNull PLVPPTLocalCacheVO pLVPPTLocalCacheVO);

    void pause(String str);

    void play(String str);

    void pptPrepare(String str);

    void seek(String str);
}
