package com.umeng.socialize.editorpage;

import android.os.Bundle;
import com.umeng.socialize.ShareContent;

/* loaded from: classes6.dex */
public interface IEditor {
    Bundle getEditable(ShareContent shareContent);

    ShareContent getResult(ShareContent shareContent, Bundle bundle);
}
