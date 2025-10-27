package com.petterp.floatingx.util;

import com.meizu.cloud.pushsdk.constants.PushConstants;
import kotlin.Metadata;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u000f\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0006j\u0002\b\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\n¨\u0006\u000b"}, d2 = {"Lcom/petterp/floatingx/util/FxScopeEnum;", "", "tag", "", "(Ljava/lang/String;ILjava/lang/String;)V", "getTag", "()Ljava/lang/String;", "APP_SCOPE", "ACTIVITY_SCOPE", "FRAGMENT_SCOPE", "VIEW_GROUP_SCOPE", "floatingx_release"}, k = 1, mv = {1, 5, 1}, xi = 48)
/* loaded from: classes4.dex */
public enum FxScopeEnum {
    APP_SCOPE("app"),
    ACTIVITY_SCOPE(PushConstants.INTENT_ACTIVITY_NAME),
    FRAGMENT_SCOPE("fragment"),
    VIEW_GROUP_SCOPE("view");


    @NotNull
    private final String tag;

    FxScopeEnum(String str) {
        this.tag = str;
    }

    @NotNull
    public final String getTag() {
        return this.tag;
    }
}
