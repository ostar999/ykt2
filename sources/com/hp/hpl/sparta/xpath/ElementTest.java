package com.hp.hpl.sparta.xpath;

import com.hp.hpl.sparta.Sparta;

/* loaded from: classes4.dex */
public class ElementTest extends NodeTest {
    private final String tagName_;

    public ElementTest(String str) {
        this.tagName_ = Sparta.intern(str);
    }

    @Override // com.hp.hpl.sparta.xpath.NodeTest
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public String getTagName() {
        return this.tagName_;
    }

    @Override // com.hp.hpl.sparta.xpath.NodeTest
    public boolean isStringValue() {
        return false;
    }

    public String toString() {
        return this.tagName_;
    }
}
