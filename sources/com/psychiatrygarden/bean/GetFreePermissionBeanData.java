package com.psychiatrygarden.bean;

import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0014\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001c\u0010\t\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u0006\"\u0004\b\u000b\u0010\bR\u001c\u0010\f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u0006\"\u0004\b\u000e\u0010\bR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\b¨\u0006\u0018"}, d2 = {"Lcom/psychiatrygarden/bean/GetFreePermissionBeanData;", "", "()V", "free_activity_id", "", "getFree_activity_id", "()Ljava/lang/String;", "setFree_activity_id", "(Ljava/lang/String;)V", "free_times", "getFree_times", "setFree_times", "free_unlock_status", "getFree_unlock_status", "setFree_unlock_status", "permission", "getPermission", "setPermission", "receive", "getReceive", "setReceive", "type", "getType", "setType", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class GetFreePermissionBeanData {

    @Nullable
    private String free_activity_id;

    @Nullable
    private String free_times;

    @Nullable
    private String free_unlock_status;

    @Nullable
    private String permission;

    @Nullable
    private String receive;

    @Nullable
    private String type;

    @Nullable
    public final String getFree_activity_id() {
        return this.free_activity_id;
    }

    @Nullable
    public final String getFree_times() {
        return this.free_times;
    }

    @Nullable
    public final String getFree_unlock_status() {
        return this.free_unlock_status;
    }

    @Nullable
    public final String getPermission() {
        return this.permission;
    }

    @Nullable
    public final String getReceive() {
        return this.receive;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    public final void setFree_activity_id(@Nullable String str) {
        this.free_activity_id = str;
    }

    public final void setFree_times(@Nullable String str) {
        this.free_times = str;
    }

    public final void setFree_unlock_status(@Nullable String str) {
        this.free_unlock_status = str;
    }

    public final void setPermission(@Nullable String str) {
        this.permission = str;
    }

    public final void setReceive(@Nullable String str) {
        this.receive = str;
    }

    public final void setType(@Nullable String str) {
        this.type = str;
    }
}
