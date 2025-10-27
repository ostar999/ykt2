package com.plv.socket.event;

import com.plv.foundationsdk.model.PLVFoundationVO;

/* loaded from: classes5.dex */
public abstract class PLVBaseEvent implements PLVFoundationVO {
    private Object obj1;
    private Object obj2;
    private Object obj3;
    private Object obj4;
    private Object obj5;
    private Object[] objects;

    public abstract String getEVENT();

    public abstract String getListenEvent();

    public Object getObj1() {
        return this.obj1;
    }

    public Object getObj2() {
        return this.obj2;
    }

    public Object getObj3() {
        return this.obj3;
    }

    public Object getObj4() {
        return this.obj4;
    }

    public Object getObj5() {
        return this.obj5;
    }

    public Object[] getObjects() {
        return this.objects;
    }

    public void setObj1(Object obj) {
        this.obj1 = obj;
    }

    public void setObj2(Object obj) {
        this.obj2 = obj;
    }

    public void setObj3(Object obj) {
        this.obj3 = obj;
    }

    public void setObj4(Object obj) {
        this.obj4 = obj;
    }

    public void setObj5(Object obj) {
        this.obj5 = obj;
    }

    public void setObjects(Object... objArr) {
        this.objects = objArr;
    }
}
