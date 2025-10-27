package com.hp.hpl.sparta.xpath;

/* loaded from: classes4.dex */
public class AllElementTest extends NodeTest {
    static final AllElementTest INSTANCE = new AllElementTest();

    private AllElementTest() {
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
        return "*";
    }
}
