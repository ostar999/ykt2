package com.hp.hpl.sparta.xpath;

import cn.hutool.core.text.StrPool;

/* loaded from: classes4.dex */
public class ThisNodeTest extends NodeTest {
    static final ThisNodeTest INSTANCE = new ThisNodeTest();

    private ThisNodeTest() {
    }

    @Override // com.hp.hpl.sparta.xpath.NodeTest
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    @Override // com.hp.hpl.sparta.xpath.NodeTest
    public boolean isStringValue() {
        return false;
    }

    public String toString() {
        return StrPool.DOT;
    }
}
