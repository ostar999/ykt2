package com.hp.hpl.sparta.xpath;

import cn.hutool.core.text.StrPool;

/* loaded from: classes4.dex */
public class ParentNodeTest extends NodeTest {
    static final ParentNodeTest INSTANCE = new ParentNodeTest();

    private ParentNodeTest() {
    }

    @Override // com.hp.hpl.sparta.xpath.NodeTest
    public void accept(Visitor visitor) throws XPathException {
        visitor.visit(this);
    }

    @Override // com.hp.hpl.sparta.xpath.NodeTest
    public boolean isStringValue() {
        return false;
    }

    public String toString() {
        return StrPool.DOUBLE_DOT;
    }
}
