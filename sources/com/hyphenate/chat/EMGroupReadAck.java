package com.hyphenate.chat;

import com.hyphenate.chat.adapter.EMAGroupReadAck;

/* loaded from: classes4.dex */
public class EMGroupReadAck extends EMBase<EMAGroupReadAck> {
    EMAGroupReadAck emaObject;

    public EMGroupReadAck() {
        this.emaObject = new EMAGroupReadAck();
    }

    public EMGroupReadAck(EMAGroupReadAck eMAGroupReadAck) {
        this.emaObject = new EMAGroupReadAck(eMAGroupReadAck);
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ boolean equals(Object obj) {
        return super.equals(obj);
    }

    public String getAckId() {
        return this.emaObject.getAckId();
    }

    public String getContent() {
        return this.emaObject.getContent();
    }

    public int getCount() {
        return this.emaObject.getCount();
    }

    public String getFrom() {
        return this.emaObject.getFrom();
    }

    public String getMsgId() {
        return this.emaObject.getMsgId();
    }

    public long getTimestamp() {
        return this.emaObject.getTimestamp();
    }

    @Override // com.hyphenate.chat.EMBase
    public /* bridge */ /* synthetic */ int hashCode() {
        return super.hashCode();
    }
}
