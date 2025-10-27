package com.hp.hpl.sparta.xpath;

import cn.hutool.core.text.StrPool;
import com.hp.hpl.sparta.Sparta;

/* loaded from: classes4.dex */
public abstract class AttrCompareExpr extends AttrExpr {
    private final String attrValue_;

    public AttrCompareExpr(String str, String str2) {
        super(str);
        this.attrValue_ = Sparta.intern(str2);
    }

    public String getAttrValue() {
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
