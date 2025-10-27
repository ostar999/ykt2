package com.psychiatrygarden.bean;

import java.io.Serializable;
import java.util.ArrayList;

/* loaded from: classes5.dex */
public class HandoutPushDataBean implements Serializable {
    public ArrayList<ChannelItems> _default;
    public ArrayList<ChannelItems> _list;
    public ArrayList<ChannelItems> u_default;
    public ArrayList<ChannelItems> u_list;

    public ArrayList<ChannelItems> getU_default() {
        return this.u_default;
    }

    public ArrayList<ChannelItems> getU_list() {
        return this.u_list;
    }

    public ArrayList<ChannelItems> get_default() {
        return this._default;
    }

    public ArrayList<ChannelItems> get_list() {
        return this._list;
    }

    public void setU_default(ArrayList<ChannelItems> u_default) {
        this.u_default = u_default;
    }

    public void setU_list(ArrayList<ChannelItems> u_list) {
        this.u_list = u_list;
    }

    public void set_default(ArrayList<ChannelItems> _default) {
        this._default = _default;
    }

    public void set_list(ArrayList<ChannelItems> _list) {
        this._list = _list;
    }
}
