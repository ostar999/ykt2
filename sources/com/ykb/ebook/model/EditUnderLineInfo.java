package com.ykb.ebook.model;

import com.google.android.exoplayer2.source.rtsp.SessionDescription;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

@Metadata(d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\b\n\u0002\b\f\u0018\u00002\u00020\u0001B/\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\u0005\u0012\u0006\u0010\t\u001a\u00020\u0007¢\u0006\u0002\u0010\nR\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\t\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010¨\u0006\u0013"}, d2 = {"Lcom/ykb/ebook/model/EditUnderLineInfo;", "", "hasUnderLine", "", "lineId", "", "startPosition", "", "text", SessionDescription.ATTR_LENGTH, "(ZLjava/lang/String;ILjava/lang/String;I)V", "getHasUnderLine", "()Z", "getLength", "()I", "getLineId", "()Ljava/lang/String;", "getStartPosition", "getText", "ebook_release"}, k = 1, mv = {1, 8, 0}, xi = 48)
/* loaded from: classes7.dex */
public final class EditUnderLineInfo {
    private final boolean hasUnderLine;
    private final int length;

    @NotNull
    private final String lineId;
    private final int startPosition;

    @NotNull
    private final String text;

    public EditUnderLineInfo(boolean z2, @NotNull String lineId, int i2, @NotNull String text, int i3) {
        Intrinsics.checkNotNullParameter(lineId, "lineId");
        Intrinsics.checkNotNullParameter(text, "text");
        this.hasUnderLine = z2;
        this.lineId = lineId;
        this.startPosition = i2;
        this.text = text;
        this.length = i3;
    }

    public final boolean getHasUnderLine() {
        return this.hasUnderLine;
    }

    public final int getLength() {
        return this.length;
    }

    @NotNull
    public final String getLineId() {
        return this.lineId;
    }

    public final int getStartPosition() {
        return this.startPosition;
    }

    @NotNull
    public final String getText() {
        return this.text;
    }

    public /* synthetic */ EditUnderLineInfo(boolean z2, String str, int i2, String str2, int i3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? false : z2, str, i2, str2, i3);
    }
}
