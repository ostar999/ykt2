package com.hp.hpl.sparta.xpath;

/* loaded from: classes4.dex */
public class AttrTest extends NodeTest {
    private final String attrName_;

    public AttrTest(String str) {
        this.attrName_ = str;
    }

    @Override // com.hp.hpl.sparta.xpath.NodeTest
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getAttrName() {
        return this.attrName_;
    }

    @Override // com.hp.hpl.sparta.xpath.NodeTest
    public boolean isStringValue() {
        return true;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("@");
        stringBuffer.append(this.attrName_);
        return stringBuffer.toString();
    }
}
