package com.hp.hpl.sparta.xpath;

import cn.hutool.core.text.StrPool;

/* loaded from: classes4.dex */
public abstract class AttrRelationalExpr extends AttrExpr {
    private final int attrValue_;

    public AttrRelationalExpr(String str, int i2) {
        super(str);
        this.attrValue_ = i2;
    }

    public double getAttrValue() {
        return this.attrValue_;
    }

    public String toString(String str) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(StrPool.BRACKET_START);
        stringBuffer.append(super.toString());
        stringBuffer.append(str);
        stringBuffer.append("'");
        stringBuffer.append(this.attrValue_);
        stringBuffer.append("']");
        return stringBuffer.toString();
    }
}
