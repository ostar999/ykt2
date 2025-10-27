package com.plv.livescenes.model;

import com.plv.socket.user.PLVSocketUserBean;
import java.util.List;

/* loaded from: classes5.dex */
public class PLVListUsersVO {
    private int count;
    private List<PLVSocketUserBean> userlist;

    public int getCount() {
        return this.count;
    }

    public List<PLVSocketUserBean> getUserlist() {
        return this.userlist;
    }

    public void setCount(int i2) {
        this.count = i2;
    }

    public void setUserlist(List<PLVSocketUserBean> list) {
        this.userlist = list;
    }

    public String toString() {
        return "PLVListUsersVO{count=" + this.count + ", userlist=" + this.userlist + '}';
    }
}
