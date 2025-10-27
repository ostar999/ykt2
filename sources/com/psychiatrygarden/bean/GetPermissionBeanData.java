package com.psychiatrygarden.bean;

import com.tencent.mm.opensdk.constants.ConstantsAPI;
import java.util.List;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u001d\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001c\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\"\u0010\t\u001a\n\u0012\u0004\u0012\u00020\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\u0006\"\u0004\b\u0011\u0010\bR\u001c\u0010\u0012\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\u0006\"\u0004\b\u0014\u0010\bR\u001c\u0010\u0015\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\u0006\"\u0004\b\u0017\u0010\bR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\u0006\"\u0004\b\u001a\u0010\bR\u001c\u0010\u001b\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001c\u0010\u0006\"\u0004\b\u001d\u0010\bR\u001c\u0010\u001e\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001f\u0010\u0006\"\u0004\b \u0010\bR\u001c\u0010!\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\"\u0010\u0006\"\u0004\b#\u0010\bR\u001c\u0010$\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b%\u0010\u0006\"\u0004\b&\u0010\b¨\u0006'"}, d2 = {"Lcom/psychiatrygarden/bean/GetPermissionBeanData;", "", "()V", ConstantsAPI.WXWebPage.KEY_ACTIVITY_ID, "", "getActivity_id", "()Ljava/lang/String;", "setActivity_id", "(Ljava/lang/String;)V", "content", "", "getContent", "()Ljava/util/List;", "setContent", "(Ljava/util/List;)V", "free_times", "getFree_times", "setFree_times", "free_times_status", "getFree_times_status", "setFree_times_status", "permission", "getPermission", "setPermission", "permission_unlock_status", "getPermission_unlock_status", "setPermission_unlock_status", "sub_title", "getSub_title", "setSub_title", "tip", "getTip", "setTip", "title", "getTitle", "setTitle", "type", "getType", "setType", "xizongapp_ykbRelease"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes5.dex */
public final class GetPermissionBeanData {

    @Nullable
    private String activity_id;

    @Nullable
    private List<String> content;

    @Nullable
    private String free_times;

    @Nullable
    private String free_times_status;

    @Nullable
    private String permission;

    @Nullable
    private String permission_unlock_status;

    @Nullable
    private String sub_title;

    @Nullable
    private String tip;

    @Nullable
    private String title;

    @Nullable
    private String type;

    @Nullable
    public final String getActivity_id() {
        return this.activity_id;
    }

    @Nullable
    public final List<String> getContent() {
        return this.content;
    }

    @Nullable
    public final String getFree_times() {
        return this.free_times;
    }

    @Nullable
    public final String getFree_times_status() {
        return this.free_times_status;
    }

    @Nullable
    public final String getPermission() {
        return this.permission;
    }

    @Nullable
    public final String getPermission_unlock_status() {
        return this.permission_unlock_status;
    }

    @Nullable
    public final String getSub_title() {
        return this.sub_title;
    }

    @Nullable
    public final String getTip() {
        return this.tip;
    }

    @Nullable
    public final String getTitle() {
        return this.title;
    }

    @Nullable
    public final String getType() {
        return this.type;
    }

    public final void setActivity_id(@Nullable String str) {
        this.activity_id = str;
    }

    public final void setContent(@Nullable List<String> list) {
        this.content = list;
    }

    public final void setFree_times(@Nullable String str) {
        this.free_times = str;
    }

    public final void setFree_times_status(@Nullable String str) {
        this.free_times_status = str;
    }

    public final void setPermission(@Nullable String str) {
        this.permission = str;
    }

    public final void setPermission_unlock_status(@Nullable String str) {
        this.permission_unlock_status = str;
    }

    public final void setSub_title(@Nullable String str) {
        this.sub_title = str;
    }

    public final void setTip(@Nullable String str) {
        this.tip = str;
    }

    public final void setTitle(@Nullable String str) {
        this.title = str;
    }

    public final void setType(@Nullable String str) {
        this.type = str;
    }
}
