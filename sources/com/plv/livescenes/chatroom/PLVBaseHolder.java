package com.plv.livescenes.chatroom;

import java.util.Arrays;

/* loaded from: classes4.dex */
public abstract class PLVBaseHolder {
    private Object[] objects;

    public Object[] getObjects() {
        return this.objects;
    }

    public void setObjects(Object... objArr) {
        this.objects = objArr;
    }

    public String toString() {
        return "PLVBaseHolder{objects=" + Arrays.toString(this.objects) + '}';
    }
}
