package com.hyphenate.chat;

import java.util.List;

/* loaded from: classes4.dex */
public class EMCursorResult<T> extends EMResult<List<T>> {
    private String cursor = "";

    public String getCursor() {
        return this.cursor;
    }

    @Override // com.hyphenate.chat.EMResult
    public /* bridge */ /* synthetic */ Object getData() {
        return super.getData();
    }

    public void setCursor(String str) {
        this.cursor = str;
    }
}
