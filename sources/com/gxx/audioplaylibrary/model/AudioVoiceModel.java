package com.gxx.audioplaylibrary.model;

import android.net.Uri;
import kotlin.Metadata;
import org.jetbrains.annotations.Nullable;

@Metadata(d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u000b\n\u0002\u0010\u0007\n\u0002\b\u0005\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001e\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0086\u000e¢\u0006\u0010\n\u0002\u0010\b\u001a\u0004\b\u0003\u0010\u0005\"\u0004\b\u0006\u0010\u0007R\u001c\u0010\t\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001c\u0010\u000f\u001a\u0004\u0018\u00010\u0010X\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u0012\"\u0004\b\u0013\u0010\u0014R\u001c\u0010\u0015\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0016\u0010\f\"\u0004\b\u0017\u0010\u000eR\u001c\u0010\u0018\u001a\u0004\u0018\u00010\nX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0019\u0010\f\"\u0004\b\u001a\u0010\u000eR\u001a\u0010\u001b\u001a\u00020\u001cX\u0086\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u001d\u0010\u001e\"\u0004\b\u001f\u0010 ¨\u0006!"}, d2 = {"Lcom/gxx/audioplaylibrary/model/AudioVoiceModel;", "", "()V", "isTelephoneReceiverPlay", "", "()Ljava/lang/Boolean;", "setTelephoneReceiverPlay", "(Ljava/lang/Boolean;)V", "Ljava/lang/Boolean;", "playIngAssetsName", "", "getPlayIngAssetsName", "()Ljava/lang/String;", "setPlayIngAssetsName", "(Ljava/lang/String;)V", "playIngFileUri", "Landroid/net/Uri;", "getPlayIngFileUri", "()Landroid/net/Uri;", "setPlayIngFileUri", "(Landroid/net/Uri;)V", "playIngRemoteUrl", "getPlayIngRemoteUrl", "setPlayIngRemoteUrl", "playIngVoiceId", "getPlayIngVoiceId", "setPlayIngVoiceId", "speed", "", "getSpeed", "()F", "setSpeed", "(F)V", "mvvm_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes4.dex */
public final class AudioVoiceModel {

    @Nullable
    private Boolean isTelephoneReceiverPlay;

    @Nullable
    private String playIngAssetsName;

    @Nullable
    private Uri playIngFileUri;

    @Nullable
    private String playIngRemoteUrl;

    @Nullable
    private String playIngVoiceId;
    private float speed = 1.0f;

    @Nullable
    public final String getPlayIngAssetsName() {
        return this.playIngAssetsName;
    }

    @Nullable
    public final Uri getPlayIngFileUri() {
        return this.playIngFileUri;
    }

    @Nullable
    public final String getPlayIngRemoteUrl() {
        return this.playIngRemoteUrl;
    }

    @Nullable
    public final String getPlayIngVoiceId() {
        return this.playIngVoiceId;
    }

    public final float getSpeed() {
        return this.speed;
    }

    @Nullable
    /* renamed from: isTelephoneReceiverPlay, reason: from getter */
    public final Boolean getIsTelephoneReceiverPlay() {
        return this.isTelephoneReceiverPlay;
    }

    public final void setPlayIngAssetsName(@Nullable String str) {
        this.playIngAssetsName = str;
    }

    public final void setPlayIngFileUri(@Nullable Uri uri) {
        this.playIngFileUri = uri;
    }

    public final void setPlayIngRemoteUrl(@Nullable String str) {
        this.playIngRemoteUrl = str;
    }

    public final void setPlayIngVoiceId(@Nullable String str) {
        this.playIngVoiceId = str;
    }

    public final void setSpeed(float f2) {
        this.speed = f2;
    }

    public final void setTelephoneReceiverPlay(@Nullable Boolean bool) {
        this.isTelephoneReceiverPlay = bool;
    }
}
