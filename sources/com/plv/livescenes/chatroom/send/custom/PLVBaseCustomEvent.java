package com.plv.livescenes.chatroom.send.custom;

import cn.hutool.core.text.CharPool;
import com.easefun.polyv.livescenes.chatroom.PolyvBaseHolder;

/* loaded from: classes4.dex */
public class PLVBaseCustomEvent<DataBean> extends PolyvBaseHolder {
    public static final int EMITMODE_ALL = 0;
    public static final int EMITMODE_ONE = 3;
    public static final int EMITMODE_OTHERS = 1;
    public static final int EMITMODE_OWN = 2;
    public static final int EMITMODE_TEACHER = 4;
    public static final String TIP_DEFAULT = "发送了自定义消息";
    protected String EVENT;
    protected DataBean data;
    protected int emitMode;
    protected String id;
    protected boolean joinHistoryList;
    protected String tip;

    public PLVBaseCustomEvent(String str, DataBean databean) {
        this(str, 0, databean);
    }

    public DataBean getData() {
        return this.data;
    }

    public String getEVENT() {
        return this.EVENT;
    }

    public int getEmitMode() {
        return this.emitMode;
    }

    public String getId() {
        return this.id;
    }

    public boolean getJoinHistory() {
        return this.joinHistoryList;
    }

    public String getTip() {
        return this.tip;
    }

    public void setData(DataBean databean) {
        this.data = databean;
    }

    public void setEVENT(String str) {
        this.EVENT = str;
    }

    public void setEmitMode(int i2) {
        this.emitMode = i2;
    }

    public void setId(String str) {
        this.id = str;
    }

    public void setJoinHistory(boolean z2) {
        this.joinHistoryList = z2;
    }

    public void setTip(String str) {
        this.tip = str;
    }

    @Override // com.plv.livescenes.chatroom.PLVBaseHolder
    public String toString() {
        return "PLVBaseCustomEvent{EVENT='" + this.EVENT + CharPool.SINGLE_QUOTE + ", tip='" + this.tip + CharPool.SINGLE_QUOTE + ", emitMode=" + this.emitMode + ", id=" + this.id + ", data=" + this.data + '}';
    }

    public PLVBaseCustomEvent(String str, String str2, DataBean databean) {
        this(str, 0, str2, databean);
    }

    public PLVBaseCustomEvent(String str, int i2, DataBean databean) {
        this(str, i2, TIP_DEFAULT, databean);
    }

    public PLVBaseCustomEvent(String str, int i2, String str2, DataBean databean) {
        this.EVENT = str;
        this.emitMode = i2;
        this.tip = str2;
        this.data = databean;
    }

    public PLVBaseCustomEvent(String str, int i2, String str2, DataBean databean, boolean z2) {
        this.EVENT = str;
        this.emitMode = i2;
        this.tip = str2;
        this.data = databean;
        this.joinHistoryList = z2;
    }
}
